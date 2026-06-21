package com.example._0zo.model.game;

import com.example._0zo.controller.GameEventListener;
import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.GameOverException;
import com.example._0zo.model.exceptions.InvalidMoveException;
import com.example._0zo.model.players.HumanPlayer;
import com.example._0zo.model.players.MachinePlayer;
import com.example._0zo.model.players.Player;

import java.util.concurrent.*;

public class TurnManager {
    private static final long MACHINE_PLAY_MIN_MS = 2_000L;
    private static final long MACHINE_PLAY_MAX_MS = 4_000L;
    private static final long MACHINE_DRAW_MIN_MS = 1_000L;
    private static final long MACHINE_DRAW_MAX_MS = 2_000L;
    private final  GameEngine engine;
    private final GameEventListener listener;
    private Thread turnThread;
    private ScheduledExecutorService machineScheduler;
    private final BlockingQueue<Card> humanCardQueue = new LinkedBlockingQueue<>(1);
    private volatile boolean running = false;
    public TurnManager(GameEngine engine, GameEventListener listener) {
        this.engine   = engine;
        this.listener = listener;
    }
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
    public void submitHumanCard(Card card) {
        humanCardQueue.offer(card);
    }
    public void stopGame() {
        running = false;
        if (machineScheduler != null) {
            machineScheduler.shutdownNow();
        }
        if (turnThread != null) {
            turnThread.interrupt();
        }
    }
    private void runTurnLoop() {
        while (running && engine.getState().isGameRunning()) {
            try {
                Player current = engine.getCurrentPlayer();
                if (current == null) break;

                // Notify controller that a new turn has started
                notifyOnFxThread(() -> listener.onTurnStarted(current));

                // --- HU-5: check elimination before the player acts ---
                boolean eliminated = engine.eliminateCurrentPlayer();
                if (eliminated) {
                    Player eliminated_player = findJustEliminatedPlayer();
                    notifyOnFxThread(() -> listener.onPlayerEliminated(eliminated_player));
                    // GameOverException would have been thrown inside
                    // eliminateCurrentPlayer() if game is over, so we just
                    // continue to the next turn here.
                    engine.advanceTurn();
                    continue;
                }

                // --- Act based on player type ---
                if (current instanceof HumanPlayer human) {
                    handleHumanTurn(human);
                } else if (current instanceof MachinePlayer machine) {
                    handleMachineTurn(machine);
                }

                // Advance to the next player
                engine.advanceTurn();

            } catch (GameOverException e) {
                // Game ended (either elimination or a successful play revealed
                // only one player left — handled inside engine methods)
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
    private void handleHumanTurn(HumanPlayer human)
            throws InterruptedException, GameOverException {

        boolean cardPlayed = false;

        while (!cardPlayed) {
            // Block here until the UI calls submitHumanCard()
            Card chosen = humanCardQueue.take();

            try {
                engine.playHumanCard(human, chosen);
                Card topCard = engine.getTopCard();
                int  newSum  = engine.getTableSum();

                notifyOnFxThread(() -> listener.onCardPlayed(human, topCard, newSum));
                cardPlayed = true;

            } catch (InvalidMoveException e) {
                // Invalid choice — tell the UI and wait for another card
                notifyOnFxThread(() -> listener.onInvalidMove(e.getMessage()));
            }
        }

        // HU-4: draw a replacement card (after playing)
        drawWithNotification(human);
    }
    private void handleMachineTurn(MachinePlayer machine)
            throws InterruptedException, GameOverException {

        // We need to propagate exceptions from the scheduled task back to
        // Thread 1. We use a single-element blocking queue for that.
        BlockingQueue<Exception> errorQueue = new LinkedBlockingQueue<>(1);

        long playDelay = randomBetween(MACHINE_PLAY_MIN_MS, MACHINE_PLAY_MAX_MS);

        // Schedule: machine plays its card on Thread 2
        machineScheduler.schedule(() -> {
            try {
                engine.playMachineTurn(machine);
                Card topCard = engine.getTopCard();
                int  newSum  = engine.getTableSum();
                notifyOnFxThread(() -> listener.onCardPlayed(machine, topCard, newSum));

            } catch (InvalidMoveException | GameOverException e) {
                errorQueue.offer(e);
            }
        }, playDelay, TimeUnit.MILLISECONDS);

        // Thread 1 waits until the scheduled task has put something in
        // errorQueue OR the play delay + a small buffer has passed.
        // We poll with a generous timeout to avoid hanging forever.
        Exception error = errorQueue.poll(
                playDelay + MACHINE_PLAY_MAX_MS + 1_000L, TimeUnit.MILLISECONDS);

        if (error instanceof GameOverException goe) throw goe;
        if (error instanceof InvalidMoveException) {
            return;
        }
        long drawDelay = randomBetween(MACHINE_DRAW_MIN_MS, MACHINE_DRAW_MAX_MS);
        Thread.sleep(drawDelay);
        drawWithNotification(machine);
    }
    private void drawWithNotification(Player player) {
        engine.drawCard(player);
        Card drawn    = player.getHand().get(player.getHandSize() - 1);
        int  deckSize = engine.getDeckSize();
        notifyOnFxThread(() -> listener.onCardDrawn(player, drawn, deckSize));
    }
    private Player findJustEliminatedPlayer() {
        return engine.getState().getAllPlayers().stream()
                .filter(p -> !p.isActive())
                .reduce((first, second) -> second)   // last in the list
                .orElse(null);
    }
    private long randomBetween(long min, long max) {
        return min + (long) (Math.random() * (max - min + 1));
    }
    private void notifyOnFxThread(Runnable task) {
        javafx.application.Platform.runLater(task);
    }


}
