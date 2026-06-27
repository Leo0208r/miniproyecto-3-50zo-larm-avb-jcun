# Cincuentazo (50zo) - Poker Game

A JavaFX-based poker game for 1 to 3 players. Play against AI opponents or with other players in this classic card game implementation.

## Authors

- **Leonardo Rosero** - 2518313
- **Alejandro Velez** - 2521169
- **Julio Cesar** - 2517931


## Features

- **Multiplayer Support**: Play with 1 to 3 players
- **AI Opponents**: Intelligent computer-controlled players
- **Graphical Interface**: Built with JavaFX for a modern, intuitive UI
- **Game Management**: Menu system for starting games and viewing results
- **Card System**: Full deck management with standard poker mechanics

## Requirements

- **Java**: JDK 17 or higher
- **Maven**: 3.6+ (or use the included Maven wrapper)
- **Operating System**: Windows, macOS, or Linux

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Leo0208r/miniproyecto-3-50zo-larm-avb-jcun.git
   cd miniproyecto-3-50zo-larm-avb-jcun
   ```

2. Verify Java 17+ is installed:
   ```bash
   java -version
   ```

## Running the Game

### Using Maven Wrapper (Recommended)

**On macOS/Linux:**
```bash
./mvnw clean javafx:run
```

**On Windows:**
```bash
mvnw.cmd clean javafx:run
```

### Using Maven (if installed globally)
```bash
mvn clean javafx:run
```

The game window will launch automatically with the main menu.

## Project Structure

```
src/main/java/com/example/_0zo/
├── Main.java                          # Application entry point
├── controller/
│   ├── MenuController.java            # Menu interactions
│   ├── GameController.java            # Main game logic & UI updates
│   ├── EndController.java             # End-game screen
│   └── GameEventListener.java         # Event handling interface
├── model/
│   ├── Card.java                      # Card representation
│   ├── Deck.java                      # Deck management
│   ├── Table.java                     # Game table state
│   ├── game/                          # Game-specific logic
│   ├── players/                       # Player classes & AI
│   ├── enums/                         # Game enumerations
│   └── exceptions/                    # Custom exceptions
└── view/
    ├── MenuStage.java                 # Menu screen management
    └── (GameStage, EndStage, etc.)    # Other UI stage managers

src/main/resources/com/example/_0zo/
├── menu-view.fxml                     # Menu screen layout
├── game-view.fxml                     # Game screen layout
├── end.fxml                           # End-game screen layout
├── Icons/                             # UI icon assets
└── images/                            # Card & game graphics
```

## Game Flow

1. **Start**: Launch the application to see the main menu
2. **Setup**: Select number of players and configure game options
3. **Play**: Play hands of poker against AI or other players
4. **Results**: View game results and statistics on the end screen
5. **Replay**: Return to menu to start a new game

## Architecture

The project follows the **MVC (Model-View-Controller)** pattern:

- **Model** (`model/`): Game logic, card management, player states
- **View** (`view/` + FXML files): JavaFX UI definitions and stage management
- **Controller** (`controller/`): Bridges model and view, handles user interactions

## Technologies Used

- **Java 17**: Core language
- **JavaFX 17.0.14**: GUI framework
- **FXML**: XML-based UI markup
- **Maven**: Build automation
- **JUnit 5**: Unit testing framework

## Building

To compile the project without running it:

```bash
./mvnw clean compile
```

To package as a JAR:

```bash
./mvnw clean package
```

## Testing

Run the test suite:

```bash
./mvnw test
```

## License

No license specified. See repository for details.

## Contributing

This is a course project. For contributions or issues, please contact the project maintainers.

---

**Last Updated**: June 2026
