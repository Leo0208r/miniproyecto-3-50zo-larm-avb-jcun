# Guía de Ejecución - Cincuentazo Game

## Requisitos Previos

- Java 17 o superior instalado
- Maven 3.6+ (o usar mvnw.cmd incluido en el proyecto)

## Cómo Compilar

### Opción 1: Usar Maven Wrapper (Recomendado)
```bash
cd miniproyecto-3-50zo-larm-avb-jcun
.\mvnw.cmd clean compile
```

### Opción 2: Usar Maven instalado
```bash
cd miniproyecto-3-50zo-larm-avb-jcun
mvn clean compile
```

## Cómo Ejecutar

### Opción 1: Ejecutar desde IntelliJ IDEA
1. Abre el proyecto en IntelliJ IDEA
2. Navega a `src/main/java/com/example/_0zo/Main.java`
3. Haz clic derecho en la clase y selecciona "Run 'Main.main()'"

### Opción 2: Ejecutar desde línea de comandos
```bash
cd miniproyecto-3-50zo-larm-avb-jcun
.\mvnw.cmd exec:java -Dexec.mainClass="com.example._0zo.Main"
```

### Opción 3: Ejecutar el JAR empaquetado
```bash
cd miniproyecto-3-50zo-larm-avb-jcun
.\mvnw.cmd package -DskipTests
java -m javafx.graphics -jar target/Cincuentazo-1.0-SNAPSHOT.jar com.example._0zo.Main
```

## Controles del Juego

### Pantalla de Menú
1. **Selecciona cantidad de jugadores máquina**: Haz clic en uno de los botones (1, 2, o 3)
2. **Inicia el juego**: Haz clic en el botón "Jugar"

### Pantalla de Juego
1. **Jugar una carta**: Haz clic en una carta de tu mano para jugarla
   - La carta debe cumplir con la regla de no exceder 50 en la mesa
   - Si el movimiento es inválido, verás un mensaje en el log
2. **Observar el juego**: Los turnos de los jugadores máquina ocurren automáticamente (2-4 segundos)
3. **Verificar estado**: 
   - El contador de suma en la parte superior muestra la suma actual
   - El contador del mazo muestra cuántas cartas quedan
   - El label de turno muestra de quién es el turno

### Pantalla de Fin de Juego
1. **Ver ganador**: El nombre del ganador se muestra en la pantalla
2. **Jugar nuevamente**: Haz clic en "Revancha" para volver al menú
3. **Volver al menú**: Haz clic en "Volver al menú"

## Reglas del Juego

- **Objetivo**: Ser el último jugador en quedar en juego
- **Regla principal**: La suma en la mesa no debe exceder 50
- **Valores de cartas**:
  - 2-8, 10: Suman su número
  - 9: No suma ni resta (0)
  - J, Q, K: Restan 10
  - A: Suma 1 o 10 según convenga (se elige automáticamente)
- **Eliminación**: Un jugador es eliminado si no tiene ninguna carta que pueda jugar sin exceder 50

## Estructura del Proyecto

```
miniproyecto-3-50zo-larm-avb-jcun/
├── src/main/java/com/example/_0zo/
│   ├── Main.java                    (Punto de entrada)
│   ├── controller/
│   │   ├── MenuController.java      (Controlador de menú)
│   │   ├── GameController.java      (Controlador de juego principal)
│   │   ├── EndController.java       (Controlador de fin de juego)
│   │   └── GameEventListener.java   (Interfaz de eventos)
│   ├── model/
│   │   ├── Card.java               (Modelo de carta)
│   │   ├── Deck.java               (Modelo de mazo)
│   │   ├── Table.java              (Modelo de mesa)
│   │   ├── game/
│   │   │   ├── GameEngine.java     (Motor del juego)
│   │   │   ├── GameState.java      (Estado del juego)
│   │   │   └── TurnManager.java    (Gestor de turnos)
│   │   ├── players/
│   │   │   ├── Player.java         (Clase base de jugador)
│   │   │   ├── HumanPlayer.java    (Jugador humano)
│   │   │   └── MachinePlayer.java  (Jugador máquina)
│   │   ├── enums/
│   │   │   ├── Rank.java           (Rango de carta)
│   │   │   ├── Suit.java           (Palo de carta)
│   │   │   └── PlayerStatus.java   (Estado de jugador)
│   │   └── exceptions/
│   │       ├── InvalidMoveException.java
│   │       ├── EmptyDeckException.java
│   │       └── GameOverException.java
│   └── view/
│       ├── MenuStage.java          (Gestor de pantalla de menú)
│       ├── GameStage.java          (Gestor de pantalla de juego)
│       └── EndStage.java           (Gestor de pantalla de fin)
├── src/main/resources/com/example/_0zo/
│   ├── menu-view.fxml              (Interfaz de menú)
│   ├── game.fxml                   (Interfaz de juego)
│   ├── end.fxml                    (Interfaz de fin de juego)
│   ├── Icons/
│   │   └── poker.png               (Icono de la aplicación)
│   └── images/cards/               (Imágenes de cartas)
├── pom.xml                         (Configuración de Maven)
└── mvnw.cmd / mvnw                 (Maven Wrapper)
```

## Solución de Problemas

### Error: "Failed to load game.fxml"
- Asegúrate de que los FXML estén en `src/main/resources/com/example/_0zo/`
- Verifica que los nombres de archivo sean exactos

### Error: "No human player found"
- Revisa que al menos un jugador sea una instancia de `HumanPlayer`
- Verifica que `MenuController` cree correctamente los jugadores

### El juego no responde a clics
- Asegúrate de que sea el turno del jugador humano
- Verifica que la carta seleccionada sea válida (no exceda 50)

### Los jugadores máquina no juegan
- Verifica que `TurnManager.startGame()` haya sido llamado
- Asegúrate de que los hilos estén permitidos en tu entorno

## Características Implementadas

✅ Interfaz gráfica con JavaFX
✅ Arquitectura MVC
✅ Eventos personalizados (GameEventListener)
✅ Concurrencia con hilos (TurnManager)
✅ Manejo robusto de excepciones
✅ Lógica de juego completa
✅ Gestión de turnos automática
✅ Validación de movimientos
✅ Pantalla de resultados

## Notas de Desarrollo

- El proyecto usa Java 17 con módulos (module-info.java)
- JavaFX debe estar correctamente configurado en el classpath
- Los hilos de máquina son daemons y se limpian automáticamente al finalizar
- Los eventos se ejecutan siempre en el FX Thread para evitar excepciones de sincronización

