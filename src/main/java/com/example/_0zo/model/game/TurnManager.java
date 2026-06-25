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

    private static final long MACHINE_PLAY_MIN_MS = 1_500L;
    private static final long MACHINE_PLAY_MAX_MS = 2_500L;
    private static final long MACHINE_DRAW_MIN_MS =   500L;
    private static final long MACHINE_DRAW_MAX_MS = 1_000L;

    private final GameEngine          engine;
    private final GameEventListener   listener;
    private Thread                    turnThread;
    private ScheduledExecutorService  machineScheduler;
    private final BlockingQueue<Card> humanCardQueue = new LinkedBlockingQueue<>(1);
    private volatile boolean          running = false;

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
        if (machineScheduler != null) machineScheduler.shutdownNow();
        if (turnThread       != null) turnThread.interrupt();
    }

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

    private void drawWithNotification(Player player) {
        engine.drawCard(player);
        int   deckSize = engine.getDeckSize();
        Card  drawn    = player.getHand().get(player.getHandSize() - 1);
        notifyOnFxThread(() -> listener.onCardDrawn(player, drawn, deckSize));
    }

    private long randomBetween(long min, long max) {
        return min + (long) (Math.random() * (max - min + 1));
    }

    private void notifyOnFxThread(Runnable task) {
        javafx.application.Platform.runLater(task);
    }
}
