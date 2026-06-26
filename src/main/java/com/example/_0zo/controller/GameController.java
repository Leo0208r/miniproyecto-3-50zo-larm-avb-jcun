package com.example._0zo.controller;

import com.example._0zo.model.Card;
import com.example._0zo.model.game.GameEngine;
import com.example._0zo.model.game.TurnManager;
import com.example._0zo.model.players.HumanPlayer;
import com.example._0zo.model.players.Player;
import com.example._0zo.view.EndStage;
import com.example._0zo.view.GameStage;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JavaFX controller for the main game screen.
 *
 * <p>Implements {@link GameEventListener} to receive game events from the
 * {@link com.example._0zo.model.game.TurnManager} running on a background thread.
 * All UI mutations are dispatched via {@link javafx.application.Platform#runLater}.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Initialising the {@link com.example._0zo.model.game.GameEngine} and
 *       {@link com.example._0zo.model.game.TurnManager} on startup.</li>
 *   <li>Rendering player hands (face-up for the human, face-down for machines).</li>
 *   <li>Displaying the top card and cumulative sum with colour-coded urgency.</li>
 *   <li>Forwarding human card-click events to the {@code TurnManager}.</li>
 *   <li>Logging game events to the side panel.</li>
 *   <li>Transitioning to the {@link com.example._0zo.view.EndStage} when the game ends.</li>
 * </ul>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class GameController implements GameEventListener {

    // Top bar
    @FXML private Label deckInfoLabel;
    @FXML private Label sumBadge;
    @FXML private Label turnLabel;

    // Opponents
    @FXML private Label opp1Name;
    @FXML private Label opp2Name;
    @FXML private Label opp3Name;
    @FXML private HBox  opp1Cards;
    @FXML private HBox  opp2Cards;
    @FXML private HBox  opp3Cards;
    @FXML private HBox  opponentsRow;

    // Table
    @FXML private StackPane tableCardPane;
    @FXML private Label     tableCardRank;
    @FXML private Label     tableCardSuit;
    @FXML private Label     tableCardHint;
    @FXML private Label     lastValueBadge;
    @FXML private Label     deckPileCount;
    @FXML private StackPane paneDeck;

    // Human hand
    @FXML private HBox humanHandBox;

    // Log
    @FXML private TextArea logArea;

    // State
    private GameEngine   gameEngine;
    private TurnManager  turnManager;
    private HumanPlayer  humanPlayer;
    private List<Player> allPlayers;
    private Map<Player, HBox> playerCardDisplays;

    // Card image cache
    private final Map<String, Image> imageCache = new HashMap<>();

    /**
     * Initialises the controller, sets up the game engine and starts the turn loop.
     */
    @FXML

    public void initialize() {
        playerCardDisplays = new HashMap<>();
        allPlayers = GameStage.getPlayers();

        if (allPlayers == null || allPlayers.isEmpty()) {
            logMessage("Error: No players provided!");
            return;
        }

        humanPlayer = (HumanPlayer) allPlayers.stream()
                .filter(p -> p instanceof HumanPlayer)
                .findFirst().orElse(null);

        if (humanPlayer == null) { logMessage("Error: No human player!"); return; }

        gameEngine  = new GameEngine(allPlayers);
        try { gameEngine.setupGame(); }
        catch (Exception e) { logMessage("Error setup: " + e.getMessage()); return; }

        turnManager = new TurnManager(gameEngine, this);
        setupPlayerDisplays();
        updateUI();
        turnManager.startGame();
    }

    // ─── Setup ────────────────────────────────────────────────────────────────

    /**
     * Associates each player with an HBox display slot and hides unused opponent panels.
     */
    private void setupPlayerDisplays() {
        playerCardDisplays.put(allPlayers.get(0), humanHandBox);

        List<HBox>  oppBoxes  = List.of(opp1Cards, opp2Cards, opp3Cards);
        List<Label> oppNames  = List.of(opp1Name,  opp2Name,  opp3Name);

        int idx = 0;
        for (int i = 1; i < allPlayers.size(); i++) {
            Player opp = allPlayers.get(i);
            playerCardDisplays.put(opp, oppBoxes.get(idx));
            oppNames.get(idx).setText("🤖 " + opp.getName());
            idx++;
        }
        // Hide unused opponent slots
        for (int i = allPlayers.size() - 1; i < 3; i++) {
            oppBoxes.get(i).setVisible(false);
            oppNames.get(i).setVisible(false);
            // Also hide parent VBox
            if (oppBoxes.get(i).getParent() instanceof VBox vb) vb.setVisible(false);
        }
    }

    // ─── UI update ────────────────────────────────────────────────────────────

    /**
     * Refreshes all UI components to reflect the current game state.
     */
    private void updateUI() {
        updateDeckInfo();
        updateSumBadge();
        updateTableCard();
        updatePlayerHands();
        updateTurnLabel();
    }

    /**
     * Updates the deck-size labels in the top bar and the deck pile counter.
     */
    private void updateDeckInfo() {
        int size = gameEngine.getDeckSize();
        deckInfoLabel.setText("👑 Mazo: " + size + " cartas");
        deckPileCount.setText(String.valueOf(size));
    }

    /**
     * Updates the sum badge colour (green → orange → red) based on how close the
     * cumulative sum is to 50.
     */
    private void updateSumBadge() {
        int sum = gameEngine.getTableSum();
        sumBadge.setText(String.valueOf(sum));
        lastValueBadge.setText(String.valueOf(sum));
        String color = sum >= 45 ? "#ef4444" : sum >= 35 ? "#f97316" : "#4ade80";
        String style = "-fx-text-fill: " + color + ";";
        sumBadge.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; " + style);
        lastValueBadge.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 24px;");
    }

    /**
     * Renders the top card on the table; uses a card image if available, otherwise
     * falls back to a text representation.
     */
    private void updateTableCard() {
        Card top = gameEngine.getTopCard();
        if (top == null) return;

        // Try to load image for top card
        Image img = getCardImage(top);
        if (img != null) {
            tableCardPane.getChildren().clear();
            ImageView iv = new ImageView(img);
            iv.setFitWidth(86);
            iv.setFitHeight(126);
            iv.setPreserveRatio(true);
            tableCardPane.getChildren().add(iv);
        } else {
            // Fallback text
            tableCardRank.setText(top.getRank().getSymbol());
            tableCardSuit.setText(top.getSuit().getSymbol());
            int val = top.getValue(gameEngine.getTableSum() - top.getValue(gameEngine.getTableSum()));
            tableCardHint.setText(val > 0 ? "+" + val : String.valueOf(val));
        }
    }

    /**
     * Re-renders the hand display for every player. Human cards are shown face-up
     * (interactive); machine cards are shown face-down.
     */
    private void updatePlayerHands() {
        for (Player player : allPlayers) {
            HBox box = playerCardDisplays.get(player);
            if (box == null) continue;
            box.getChildren().clear();

            boolean isHuman = player instanceof HumanPlayer;
            for (Card card : player.getHand()) {
                box.getChildren().add(isHuman ? createFaceCard(card) : createBackCard());
            }
        }
    }

    /**
     * Updates the turn label to show the name of the current player.
     */
    private void updateTurnLabel() {
        Player cur = gameEngine.getCurrentPlayer();
        if (cur != null) turnLabel.setText("Turno: " + cur.getName());
    }

    // ─── Card visuals ─────────────────────────────────────────────────────────

    /**
     * Creates an interactive face-up card node for the human player's hand.
     *
     * <p>Uses a card image when available; falls back to a styled text label.
     * Includes hover scale animation and a click handler that submits the card
     * to the turn manager.</p>
     *
     * @param card the card to render
     * @return a {@link javafx.scene.layout.StackPane} representing the card visually
     */
    private StackPane createFaceCard(Card card) {
        StackPane sp = new StackPane();
        sp.setMinSize(60, 86);  sp.setMaxSize(60, 86);
        sp.setCursor(javafx.scene.Cursor.HAND);

        Image img = getCardImage(card);
        if (img != null) {
            ImageView iv = new ImageView(img);
            iv.setFitWidth(58); iv.setFitHeight(84);
            iv.setPreserveRatio(true);
            sp.getChildren().add(iv);
        } else {
            // text fallback
            VBox content = new VBox(2);
            content.setAlignment(Pos.CENTER);
            boolean red = card.getSuit().name().equals("HEARTS") || card.getSuit().name().equals("DIAMONDS");
            String clr = red ? "#dc2626" : "#111";
            Label rl = new Label(card.getRank().getSymbol());
            rl.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + clr + ";");
            Label sl = new Label(card.getSuit().getSymbol());
            sl.setStyle("-fx-font-size: 16px; -fx-text-fill: " + clr + ";");
            content.getChildren().addAll(rl, sl);
            sp.setStyle("-fx-background-color: white; -fx-background-radius: 6; -fx-border-color: #ccc; -fx-border-radius: 6;");
            sp.getChildren().add(content);
        }

        // Hover scale
        sp.setOnMouseEntered(e -> { ScaleTransition s = new ScaleTransition(Duration.millis(100), sp); s.setToX(1.1); s.setToY(1.1); s.play(); });
        sp.setOnMouseExited (e -> { ScaleTransition s = new ScaleTransition(Duration.millis(100), sp); s.setToX(1.0); s.setToY(1.0); s.play(); });
        sp.setOnMouseClicked(e -> onCardSelected(card));
        return sp;
    }

    /**
     * Creates a face-down card node for an opponent's hand.
     *
     * <p>Uses the back-of-card image when available; falls back to a blue rectangle.</p>
     *
     * @return a {@link javafx.scene.layout.StackPane} showing a card back
     */
    private StackPane createBackCard() {
        StackPane sp = new StackPane();
        sp.setMinSize(50, 72); sp.setMaxSize(50, 72);

        Image back = getBackImage();
        if (back != null) {
            ImageView iv = new ImageView(back);
            iv.setFitWidth(48); iv.setFitHeight(70);
            iv.setPreserveRatio(true);
            sp.getChildren().add(iv);
        } else {
            sp.setStyle("-fx-background-color: #1e40af; -fx-background-radius: 5; -fx-border-color: #93c5fd; -fx-border-radius: 5;");
        }
        return sp;
    }

    /**
     * Retrieves (or loads and caches) the image for the given card.
     *
     * @param card the card whose image is needed
     * @return the {@link javafx.scene.image.Image}, or {@code null} if not found on the classpath
     */
    private Image getCardImage(Card card) {
        String key = cardImageKey(card);
        if (imageCache.containsKey(key)) return imageCache.get(key);
        try {
            String path = "/com/example/_0zo/images/cards/" + key + ".png";
            var url = getClass().getResource(path);
            if (url == null) return null;
            Image img = new Image(url.toString());
            imageCache.put(key, img);
            return img;
        } catch (Exception e) { return null; }
    }

    /**
     * Retrieves (or loads and caches) the card-back image.
     *
     * @return the back {@link javafx.scene.image.Image}, or {@code null} if not found
     */
    private Image getBackImage() {
        if (imageCache.containsKey("back")) return imageCache.get("back");
        try {
            var url = getClass().getResource("/com/example/_0zo/images/cards/back.png");
            if (url == null) return null;
            Image img = new Image(url.toString());
            imageCache.put("back", img);
            return img;
        } catch (Exception e) { return null; }
    }

    /**
     * Converts a {@link Card} to the filename key used to load its image.
     *
     * <p>Example: ACE of HEARTS → {@code "AH"}, TEN of CLUBS → {@code "10C"}.</p>
     *
     * @param card the card to convert
     * @return the image filename key (without extension)
     */
    private String cardImageKey(Card card) {
        String rank = switch (card.getRank()) {
            case ACE   -> "A";
            case TWO   -> "2";
            case THREE -> "3";
            case FOUR  -> "4";
            case FIVE  -> "5";
            case SIX   -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE  -> "9";
            case TEN   -> "10";
            case JACK  -> "J";
            case QUEEN -> "Q";
            case KING  -> "K";
        };
        String suit = switch (card.getSuit()) {
            case HEARTS   -> "H";
            case DIAMONDS -> "D";
            case CLUBS    -> "C";
            case SPADES   -> "S";
        };
        return rank + suit;
    }

    // ─── Actions ──────────────────────────────────────────────────────────────

    /**
     * Handles a card click from the human player.
     *
     * <p>Ignores the event if it is not the human's turn; otherwise forwards
     * the selected card to the turn manager.</p>
     *
     * @param card the card that was clicked
     */
    private void onCardSelected(Card card) {
        if (gameEngine.getCurrentPlayer() != humanPlayer) {
            logMessage("⚠ No es tu turno.");
            return;
        }
        turnManager.submitHumanCard(card);
    }

    /**
     * Handles a click on the deck pile; logs the remaining card count.
     */
    @FXML private void onDeckClicked() {
        logMessage("🃏 Mazo: " + gameEngine.getDeckSize() + " cartas restantes.");
    }

    /**
     * Displays the game rules summary in the log panel.
     */
    @FXML private void onHandleHelp() {
        logMessage("📋 Reglas: 2-8 y 10 suman; J/Q/K restan 10; 9 es neutro; A suma 1 o 10.");
    }

    /**
     * Notifies the user that undo is not available in this version.
     */
    @FXML private void onHandleUndo() {
        logMessage("↩ Deshacer no está disponible en esta versión.");
    }

    // ─── Logging ──────────────────────────────────────────────────────────────

    /**
     * Appends a message to the game log panel on the JavaFX Application Thread.
     *
     * @param msg the message to append
     */
    private void logMessage(String msg) {
        if (logArea != null) Platform.runLater(() -> logArea.appendText(msg + "\n"));
    }

    // ─── Animation helpers ────────────────────────────────────────────────────
    /**
     * Plays a brief fade-in animation on the table card pane to signal a new card was placed.
     */
    private void animateCardPlay() {
        FadeTransition ft = new FadeTransition(Duration.millis(300), tableCardPane);
        ft.setFromValue(0.3); ft.setToValue(1.0);
        ft.play();
    }

    // ─── GameEventListener ────────────────────────────────────────────────────

    @Override
    public void onTurnStarted(Player player) {
        Platform.runLater(() -> {
            updateTurnLabel();
            // Highlight human hand when it's their turn
            boolean myTurn = player instanceof HumanPlayer;
            humanHandBox.setStyle(myTurn
                ? "-fx-border-color: #fbbf24; -fx-border-width: 2; -fx-border-radius: 6; -fx-padding: 6;"
                : "-fx-padding: 6;");
        });
    }

    @Override
    public void onCardPlayed(Player player, Card card, int newSum) {
        Platform.runLater(() -> {
            updateTableCard();
            updateSumBadge();
            updatePlayerHands();
            animateCardPlay();
            logMessage("🃏 " + player.getName() + " jugó " + card.getRank().getSymbol()
                    + card.getSuit().getSymbol() + "  →  Suma: " + newSum);
        });
    }

    @Override
    public void onCardDrawn(Player player, Card card, int deckSize) {
        Platform.runLater(() -> {
            updatePlayerHands();
            updateDeckInfo();
            logMessage("📥 " + player.getName() + " robó una carta. Mazo: " + deckSize);
        });
    }

    @Override
    public void onPlayerEliminated(Player player) {
        Platform.runLater(() -> {
            updatePlayerHands();
            logMessage("💀 ¡" + player.getName() + " fue eliminado!");
        });
    }

    @Override
    public void onGameOver(Player winner, int totalRounds) {
        Platform.runLater(() -> {
            turnManager.stopGame();
            if (winner != null) logMessage("🏆 ¡FIN! Ganador: " + winner.getName() + " | Rondas: " + totalRounds);
            else                logMessage("🏁 FIN sin ganador. Rondas: " + totalRounds);
            EndStage.setWinner(winner);
            EndStage.setTotalRounds(totalRounds);
            EndStage.showView();
        });
    }

    @Override
    public void onInvalidMove(String message) {
        Platform.runLater(() -> logMessage("❌ " + message));
    }
}
