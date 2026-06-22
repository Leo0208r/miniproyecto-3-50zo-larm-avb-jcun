package com.example._0zo.controller;

import com.example._0zo.model.Card;
import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.game.GameEngine;
import com.example._0zo.model.game.TurnManager;
import com.example._0zo.model.players.HumanPlayer;
import com.example._0zo.model.players.Player;
import com.example._0zo.view.GameStage;
import com.example._0zo.view.EndStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the Game View. Handles the main game logic and UI updates.
 *
 * This controller implements GameEventListener to react to game events and
 * manages the visual representation of the game state on the screen.
 */
public class GameController implements GameEventListener {

    // Top bar labels
    @FXML
    private Label deckInfoLabel;

    @FXML
    private Label sumBadge;

    @FXML
    private Label turnLabel;

    // Opponents display
    @FXML
    private Label opp1Name;

    @FXML
    private Label opp2Name;

    @FXML
    private Label opp3Name;

    @FXML
    private HBox opp1Cards;

    @FXML
    private HBox opp2Cards;

    @FXML
    private HBox opp3Cards;

    @FXML
    private HBox opponentsRow;

    // Table area
    @FXML
    private VBox tableCard;

    @FXML
    private Label tableCardRank;

    @FXML
    private Label tableCardSuit;

    @FXML
    private Label tableCardHint;

    @FXML
    private Label lastValueBadge;

    @FXML
    private Label deckPileCount;

    // Human hand
    @FXML
    private HBox humanHandBox;

    // Log area
    @FXML
    private TextArea logArea;

    // Game state
    private GameEngine gameEngine;
    private TurnManager turnManager;
    private HumanPlayer humanPlayer;
    private List<Player> allPlayers;
    private Map<Player, HBox> playerCardDisplays;

    /**
     * Initializes the game controller. Called automatically by JavaFX.
     */
    @FXML
    public void initialize() {
        playerCardDisplays = new HashMap<>();

        // Setup the game with players from GameStage
        allPlayers = GameStage.getPlayers();

        if (allPlayers == null || allPlayers.isEmpty()) {
            logMessage("Error: No players provided!");
            return;
        }

        // Find the human player
        humanPlayer = (HumanPlayer) allPlayers.stream()
                .filter(p -> p instanceof HumanPlayer)
                .findFirst()
                .orElse(null);

        if (humanPlayer == null) {
            logMessage("Error: No human player found!");
            return;
        }

        // Initialize game engine
        gameEngine = new GameEngine(allPlayers);

        try {
            gameEngine.setupGame();
        } catch (Exception e) {
            logMessage("Error setting up game: " + e.getMessage());
            return;
        }

        // Initialize turn manager
        turnManager = new TurnManager(gameEngine, this);

        // Setup UI components
        setupPlayerDisplays();
        updateUI();

        // Start the game
        turnManager.startGame();
    }

    /**
     * Sets up the display components for all players.
     */
    private void setupPlayerDisplays() {
        // Setup opponent displays based on the number of players
        playerCardDisplays.put(allPlayers.get(0), humanHandBox); // Human player

        List<HBox> oppBoxes = List.of(opp1Cards, opp2Cards, opp3Cards);
        List<Label> oppNameLabels = List.of(opp1Name, opp2Name, opp3Name);

        int oppIndex = 0;
        for (int i = 1; i < allPlayers.size(); i++) {
            Player opponent = allPlayers.get(i);
            playerCardDisplays.put(opponent, oppBoxes.get(oppIndex));
            oppNameLabels.get(oppIndex).setText(opponent.getName());
            oppIndex++;
        }

        // Hide unused opponent displays
        if (allPlayers.size() == 2) {
            opp2Cards.setVisible(false);
            opp2Name.setVisible(false);
            opp3Cards.setVisible(false);
            opp3Name.setVisible(false);
        } else if (allPlayers.size() == 3) {
            opp3Cards.setVisible(false);
            opp3Name.setVisible(false);
        }
    }

    /**
     * Updates the UI with current game state.
     */
    private void updateUI() {
        updateDeckInfo();
        updateSumBadge();
        updateTableCard();
        updatePlayerHands();
        updateTurnLabel();
    }

    /**
     * Updates the deck information label.
     */
    private void updateDeckInfo() {
        int deckSize = gameEngine.getDeckSize();
        deckInfoLabel.setText("Deck: " + deckSize + " cards");
        deckPileCount.setText(String.valueOf(deckSize));
    }

    /**
     * Updates the sum badge with the current table sum.
     */
    private void updateSumBadge() {
        int sum = gameEngine.getTableSum();
        sumBadge.setText(String.valueOf(sum));
        lastValueBadge.setText(String.valueOf(sum));

        // Change color based on sum
        if (sum >= 45) {
            sumBadge.setStyle("-fx-text-fill: #FF6B6B;");
            lastValueBadge.setStyle("-fx-text-fill: #FF6B6B;");
        } else if (sum >= 35) {
            sumBadge.setStyle("-fx-text-fill: #FFA500;");
            lastValueBadge.setStyle("-fx-text-fill: #FFA500;");
        } else {
            sumBadge.setStyle("-fx-text-fill: #4CAF50;");
            lastValueBadge.setStyle("-fx-text-fill: #4CAF50;");
        }
    }

    /**
     * Updates the table card display.
     */
    private void updateTableCard() {
        Card topCard = gameEngine.getTopCard();
        if (topCard != null) {
            tableCardRank.setText(topCard.toString().split(" ")[0]);
            tableCardSuit.setText(getSuitSymbol(topCard));
            tableCardHint.setText(getCardHint(topCard));
        }
    }

    /**
     * Gets the symbol for a card suit.
     *
     * @param card the card
     * @return the suit symbol
     */
    private String getSuitSymbol(Card card) {
        String cardStr = card.toString();
        if (cardStr.contains("SPADE")) return "♠";
        if (cardStr.contains("HEART")) return "♥";
        if (cardStr.contains("DIAMOND")) return "♦";
        if (cardStr.contains("CLUB")) return "♣";
        return "?";
    }

    /**
     * Gets the value hint for a card.
     *
     * @param card the card
     * @return the value hint as a string
     */
    private String getCardHint(Card card) {
        int value = card.getValue(gameEngine.getTableSum());
        if (value > 0) return "+" + value;
        if (value < 0) return String.valueOf(value);
        return "0";
    }

    /**
     * Updates the player hands display.
     */
    private void updatePlayerHands() {
        for (Player player : allPlayers) {
            HBox cardBox = playerCardDisplays.get(player);
            if (cardBox != null) {
                cardBox.getChildren().clear();

                for (Card card : player.getHand()) {
                    if (player instanceof HumanPlayer) {
                        cardBox.getChildren().add(createCardPane(card, true));
                    } else {
                        cardBox.getChildren().add(createCardBackPane());
                    }
                }
            }
        }
    }

    /**
     * Creates a visual pane for a card.
     *
     * @param card the card to display
     * @param isHuman whether this card is for the human player
     * @return a Pane representing the card
     */
    private Pane createCardPane(Card card, boolean isHuman) {
        VBox cardPane = new VBox();
        cardPane.setStyle("-fx-border-color: #333; -fx-border-radius: 5; -fx-padding: 4; " +
                "-fx-background-color: white; -fx-min-width: 50; -fx-max-width: 50; " +
                "-fx-min-height: 70; -fx-max-height: 70;");
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setSpacing(2);

        String cardStr = card.toString();
        String[] parts = cardStr.split(" ");
        String rank = parts[0];
        String suit = getSuitSymbol(card);

        Label rankLabel = new Label(rank);
        rankLabel.setFont(new Font(10));
        rankLabel.setStyle("-fx-font-weight: bold;");

        Label suitLabel = new Label(suit);
        suitLabel.setFont(new Font(12));

        cardPane.getChildren().addAll(rankLabel, suitLabel);

        if (isHuman) {
            cardPane.setOnMouseClicked(event -> onCardSelected(card));
            cardPane.setCursor(javafx.scene.Cursor.HAND);
        }

        return cardPane;
    }

    /**
     * Creates a visual pane for the back of a card.
     *
     * @return a Pane representing a card back
     */
    private Pane createCardBackPane() {
        Pane cardPane = new Pane();
        cardPane.setStyle("-fx-border-color: #333; -fx-border-radius: 5; " +
                "-fx-background-color: #1976D2; -fx-min-width: 50; -fx-max-width: 50; " +
                "-fx-min-height: 70; -fx-max-height: 70;");
        return cardPane;
    }

    /**
     * Updates the turn label to show whose turn it is.
     */
    private void updateTurnLabel() {
        Player current = gameEngine.getCurrentPlayer();
        if (current != null) {
            turnLabel.setText("Turn: " + current.getName());
        }
    }

    /**
     * Handles card selection by the human player.
     *
     * @param card the selected card
     */
    private void onCardSelected(Card card) {
        if (gameEngine.getCurrentPlayer() != humanPlayer) {
            logMessage("Not your turn!");
            return;
        }

        if (!humanPlayer.getHand().contains(card)) {
            logMessage("Invalid card selection!");
            return;
        }

        turnManager.submitHumanCard(card);
    }

    /**
     * Logs a message to the log area.
     *
     * @param message the message to log
     */
    private void logMessage(String message) {
        if (logArea != null) {
            logArea.appendText(message + "\n");
        }
    }

    // GameEventListener implementation

    @Override
    public void onTurnStarted(Player player) {
        Platform.runLater(() -> {
            updateTurnLabel();
            logMessage(player.getName() + "'s turn started.");
        });
    }

    @Override
    public void onCardPlayed(Player player, Card card, int newSum) {
        Platform.runLater(() -> {
            updateTableCard();
            updateSumBadge();
            updatePlayerHands();
            logMessage(player.getName() + " played " + card + ". Sum: " + newSum);
        });
    }

    @Override
    public void onCardDrawn(Player player, Card card, int deckSize) {
        Platform.runLater(() -> {
            updatePlayerHands();
            updateDeckInfo();
            logMessage(player.getName() + " drew a card. Deck: " + deckSize + " remaining.");
        });
    }

    @Override
    public void onPlayerEliminated(Player player) {
        Platform.runLater(() -> {
            updatePlayerHands();
            logMessage("*** " + player.getName() + " has been eliminated! ***");
        });
    }

    @Override
    public void onGameOver(Player winner, int totalRounds) {
        Platform.runLater(() -> {
            turnManager.stopGame();

            if (winner != null) {
                logMessage("*** GAME OVER! Winner: " + winner.getName() + " ***");
                logMessage("Total rounds: " + totalRounds);
            } else {
                logMessage("*** GAME OVER! No winner. ***");
            }

            // Transition to end stage
            EndStage.setWinner(winner);
            EndStage.setTotalRounds(totalRounds);
            EndStage.showView();
        });
    }

    @Override
    public void onInvalidMove(String message) {
        Platform.runLater(() -> {
            logMessage("Invalid move: " + message);
        });
    }

    /**
     * Handles deck click event.
     */
    @FXML
    private void onDeckClicked() {
        // This could be used to show deck information or stats
        logMessage("Deck clicked. Cards remaining: " + gameEngine.getDeckSize());
    }
}
