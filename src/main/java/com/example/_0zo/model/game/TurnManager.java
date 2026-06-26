package com.example._0zo.model.game;

import com.example._0zo.controller.GameEventListener;
import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.GameOverException;
import com.example._0zo.model.exceptions.InvalidMoveException;
import com.example._0zo.model.players.HumanPlayer;
import com.example._0zo.model.players.MachinePlayer;
import com.example._0zo.model.players.Player;

import java.util.concurrent.*;

/**
 * Manages turn execution and timing in the poker game.
 * 
 * This class orchestrates the flow of player turns using background threads.
 * It handles both human players (waiting for UI input) and computer players
 * (with artificial delays for realism). Card play and drawing are communicated
 * to the UI via event listeners.
 * 
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */
public class TurnManager {

    /** Minimum delay (in milliseconds) for machine player card selection */
    private static final long MACHINE_PLAY_MIN_MS = 1_500L;
    /** Maximum delay (in milliseconds) for machine player card selection */
    private static final long MACHINE_PLAY_MAX_MS = 2_500L;
    /** Minimum delay (in milliseconds) for machine player card drawing */
    private static final long MACHINE_DRAW_MIN_MS =   500L;
    /** Maximum delay (in milliseconds) for machine player card drawing */
    private static final long MACHINE_DRAW_MAX_MS = 1_000L;

    /** The game engine managing game logic */
    private final GameEngine          engine;
    /** Listener for game events */
    private final GameEventListener   listener;
    /** The main turn loop thread */
    private Thread                    turnThread;
    /** Scheduler for machine player delays */
    private ScheduledExecutorService  machineScheduler;
    /** Queue for receiving human player card selections */
    private final BlockingQueue<Card> humanCardQueue = new LinkedBlockingQueue<>(1);
    /** Flag indicating if the game loop is running */
    private volatile boolean          running = false;

    /**
     * Constructs a TurnManager with the specified game engine and listener.
     *
     * @param engine the game engine
     * @param listener the event listener for game updates
     */
    public TurnManager(GameEngine engine, GameEventListener listener) {
        this.engine   = engine;
        this.listener = listener;
    }

    /**
     * Starts the game turn loop in a background thread.
     * 
     * Initializes the scheduler for machine player delays and starts the
     * main turn loop that processes each player's turn.
     */
    public void startGame() {
        running = true;
        machineScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "machine-timer");
            t.setDaemon(true);
            return t;
        });
        turnThread = new Thread(this::runTurnLoop, "turn-loop");
        turnThread.setDaemon(true);
        turnThread.start();
    }

    /**
     * Submits a card selected by a human player to the turn queue.
     * 
     * This is called from the UI when the human player clicks a card to play.
     *
     * @param card the card selected by the human player
     */
    public void submitHumanCard(Card card) {
        humanCardQueue.offer(card);
    }

    /**
     * Stops the game and all related threads.
     * 
     * Shuts down the scheduler and interrupts the turn thread.
     */
    public void stopGame() {
        running = false;
        if (machineScheduler != null) machineScheduler.shutdownNow();
        if (turnThread       != null) turnThread.interrupt();
    }

    /**
     * The main game turn loop that cycles through players.
     * 
     * This loop continues until the game ends or is interrupted. It manages
     * player elimination, calls appropriate turn handlers, and notifies listeners
     * of game events.
     */
    private void runTurnLoop() {
        while (running && engine.getState().isGameRunning()) {
            try {
                Player current = engine.getCurrentPlayer();
                if (current == null || !current.isActive()) {
                    engine.advanceTurn();
                    continue;
                }

                // Notify turn start
                final Player cur = current;
                notifyOnFxThread(() -> listener.onTurnStarted(cur));

                // Check if player can play; if not, eliminate
                if (!current.canPlay(engine.getTableSum())) {
                    boolean eliminated = engine.eliminateCurrentPlayer();
                    if (eliminated) {
                        final Player elim = current;
                        notifyOnFxThread(() -> listener.onPlayerEliminated(elim));
                    }
                    engine.advanceTurn();
                    continue;
                }

                // Act based on player type
                if (current instanceof HumanPlayer human) {
                    handleHumanTurn(human);
                } else if (current instanceof MachinePlayer machine) {
                    handleMachineTurn(machine);
                }

                engine.advanceTurn();

            } catch (GameOverException e) {
                running = false;
                Player winner = e.getWinner();
                int    rounds = e.getTotalRounds();
                notifyOnFxThread(() -> listener.onGameOver(winner, rounds));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    /**
     * Handles a human player's turn.
     * 
     * Waits for the human to select a card from the UI, validates it, and plays it.
     * If the move is invalid, the player is asked to select again.
     *
     * @param human the human player taking a turn
     * @throws InterruptedException if the thread is interrupted
     * @throws GameOverException if the game ends
     */
    private void handleHumanTurn(HumanPlayer human)
            throws InterruptedException, GameOverException {

        boolean played = false;
        while (!played) {
            Card chosen = humanCardQueue.take();
            try {
                engine.playHumanCard(human, chosen);
                Card topCard = engine.getTopCard();
                int  newSum  = engine.getTableSum();
                notifyOnFxThread(() -> listener.onCardPlayed(human, topCard, newSum));
                played = true;
            } catch (InvalidMoveException e) {
                notifyOnFxThread(() -> listener.onInvalidMove(e.getMessage()));
            }
        }
        drawWithNotification(human);
    }

    /**
     * Handles a machine player's turn with realistic timing.
     * 
     * The machine player waits a random delay before selecting and playing a card.
     * After successfully playing, another delay occurs before drawing a new card.
     *
     * @param machine the machine player taking a turn
     * @throws InterruptedException if the thread is interrupted
     * @throws GameOverException if the game ends
     */
    private void handleMachineTurn(MachinePlayer machine)
            throws InterruptedException, GameOverException {

        BlockingQueue<Exception> errorQueue = new LinkedBlockingQueue<>(1);
        long playDelay = randomBetween(MACHINE_PLAY_MIN_MS, MACHINE_PLAY_MAX_MS);

        machineScheduler.schedule(() -> {
            try {
                engine.playMachineTurn(machine);
                Card topCard = engine.getTopCard();
                int  newSum  = engine.getTableSum();
                notifyOnFxThread(() -> listener.onCardPlayed(machine, topCard, newSum));
                // Signal success with a sentinel
                errorQueue.offer(new RuntimeException("OK"));
            } catch (InvalidMoveException | GameOverException e) {
                errorQueue.offer(e);
            }
        }, playDelay, TimeUnit.MILLISECONDS);

        Exception result = errorQueue.poll(playDelay + 3_000L, TimeUnit.MILLISECONDS);

        if (result instanceof GameOverException goe) throw goe;
        if (result instanceof InvalidMoveException) return; // machine had no valid card

        // Draw delay
        Thread.sleep(randomBetween(MACHINE_DRAW_MIN_MS, MACHINE_DRAW_MAX_MS));
        drawWithNotification(machine);
    }

    /**
     * Draws a card for a player and notifies the listener.
     * 
     * Called after a player successfully plays a card. The drawn card is added
     * to the player's hand and the deck size is updated in the UI.
     *
     * @param player the player drawing a card
     */
    private void drawWithNotification(Player player) {
        engine.drawCard(player);
        int   deckSize = engine.getDeckSize();
        Card  drawn    = player.getHand().get(player.getHandSize() - 1);
        notifyOnFxThread(() -> listener.onCardDrawn(player, drawn, deckSize));
    }

    /**
     * Generates a random number between the specified min and max (inclusive).
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random long between min and max
     */
    private long randomBetween(long min, long max) {
        return min + (long) (Math.random() * (max - min + 1));
    }

    /**
     * Executes a runnable on the JavaFX application thread.
     * 
     * Used to ensure UI updates happen on the correct thread.
     *
     * @param task the task to execute on the FX thread
     */
    private void notifyOnFxThread(Runnable task) {
        javafx.application.Platform.runLater(task);
    }
}
