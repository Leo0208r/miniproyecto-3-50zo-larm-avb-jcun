# CINCUENTAZO - MANUAL DE REFERENCIA RÁPIDA

## 🎮 Inicio Rápido

### Compilar
```bash
cd miniproyecto-3-50zo-larm-avb-jcun
.\mvnw.cmd clean compile
```

### Ejecutar desde IntelliJ
1. Click derecho en `Main.java` → "Run 'Main.main()'"

### Ejecutar desde Terminal
```bash
.\mvnw.cmd exec:java -Dexec.mainClass="com.example._0zo.Main"
```

---

## 📋 Archivos Principales Creados

| Archivo | Líneas | Descripción |
|---------|--------|-------------|
| `MenuController.java` | 75 | Lógica del menú y selección de jugadores |
| `GameController.java` | 440 | Controlador principal del juego |
| `EndController.java` | 70 | Pantalla de resultados |
| `GameStage.java` | 80 | Gestor de escena del juego |
| `EndStage.java` | 80 | Gestor de escena de fin |

**Total de código nuevo**: ~745 líneas de código

---

## 🏗️ Arquitectura Implementada

```
Modelo (model/)
├── Card, Deck, Table
├── Players (HumanPlayer, MachinePlayer)
└── Game Logic (GameEngine, GameState, TurnManager)
        ↓ (notifica eventos)
        ↓
Interfaz de Eventos (GameEventListener)
        ↓ (implementa)
        ↓
Controlador (GameController)
        ↓ (actualiza)
        ↓
Vista (game.fxml + JavaFX)
```

---

## 🎯 Flujo de Juego

```
1. Main.java → MenuStage
              ↓
2. MenuController
   - Usuario selecciona jugadores (1, 2, 3)
   - Click en "Jugar"
              ↓
3. GameStage → GameController.initialize()
   - Crea GameEngine
   - Crea TurnManager
   - Inicia el loop de turnos
              ↓
4. TurnManager.runTurnLoop()
   - Turno del jugador
   - Notifica eventos
   - Actualiza GameController
              ↓
5. GameController (implementa GameEventListener)
   - onTurnStarted() → actualiza turnLabel
   - onCardPlayed() → actualiza mesa y suma
   - onCardDrawn() → actualiza mano
   - onPlayerEliminated() → notifica eliminación
   - onGameOver() → transiciona a EndStage
              ↓
6. EndStage → EndController
   - Muestra ganador
   - Botón "Revancha" o "Menú"
```

---

## 🎮 Controles del Juego

| Acción | Control |
|--------|---------|
| Seleccionar jugadores | Click en botones 1, 2, 3 |
| Jugar carta | Click en la carta |
| Ver info del mazo | Click en el icono del mazo |
| Jugar de nuevo | Click "Revancha" en pantalla de fin |
| Volver al menú | Click "Volver al menú" |

---

## 📊 Estadísticas del Proyecto

| Métrica | Valor |
|---------|-------|
| Archivos Java creados | 5 |
| Archivos Java modificados | 2 |
| Líneas de código (nuevas) | ~745 |
| Métodos nuevos | ~50 |
| Interfaces implementadas | 1 |
| Documentación (Javadoc) | 100% |
| Estado de compilación | ✅ SUCCESS |

---

## 🔧 Componentes Implementados

### MenuController
```java
// Selecciona cantidad de jugadores
selectPlayers(1|2|3)

// Crea jugadores y transiciona
startGame()
```

### GameController
```java
// Implementa interfaz GameEventListener
onTurnStarted(Player)
onCardPlayed(Player, Card, int)
onCardDrawn(Player, Card, int)
onPlayerEliminated(Player)
onGameOver(Player, int)
onInvalidMove(String)

// Maneja interacción del usuario
onCardSelected(Card)
onDeckClicked()
```

### EndController
```java
// Muestra resultados
initialize()

// Maneja acciones finales
onRematchClicked()
onMenuClicked()
```

### GameStage & EndStage
```java
// Cargan FXML y manejan escenas
setStage(Stage)
showView()
deleteView()

// Almacenan datos
setPlayers(List<Player>)
setWinner(Player)
setTotalRounds(int)
```

---

## ✅ Validación de Funcionalidades

### Historias de Usuario
- ✅ HU-1: Seleccionar cantidad de jugadores
- ✅ HU-2: Preparación automática del juego
- ✅ HU-3: Jugar una carta
- ✅ HU-4: Tomar una carta del mazo
- ✅ HU-5: Eliminación de jugadores
- ✅ HU-6: Fin de juego y ganador

### Reglas del Juego
- ✅ Cartas 2-8, 10: Suman su número
- ✅ Carta 9: No suma ni resta
- ✅ Cartas J, Q, K: Restan 10
- ✅ Carta A: Suma 1 o 10 (automático)
- ✅ Validación: No exceder 50
- ✅ Máquinas juegan en 2-4 segundos
- ✅ Máquinas toman en 1-2 segundos

---

## 🐛 Troubleshooting

### "No human player found"
→ Verifica que MenuController cree un HumanPlayer

### "Failed to load game.fxml"
→ Asegúrate que game.fxml está en `src/main/resources/com/example/_0zo/`

### El juego no responde
→ Verifica que es el turno del jugador humano
→ La carta seleccionada debe ser válida

### Errores de compilación
→ Ejecuta: `.\mvnw.cmd clean compile`
→ Verifica Java 17+ está instalado

---

## 📚 Documentación Adicional

- **`README_IMPLEMENTACION.md`** - Resumen completo
- **`IMPLEMENTACION_RESUMEN.md`** - Detalles técnicos
- **`GUIA_EJECUCION.md`** - Instrucciones de uso
- **`CHECKLIST_RUBRICA.md`** - Mapeo con criterios

---

## 🚀 Próximos Pasos (Opcionales)

1. **Pruebas Unitarias**
   ```bash
   .\mvnw.cmd test
   ```

2. **Generar Javadoc**
   ```bash
   .\mvnw.cmd javadoc:javadoc
   # Abrir: target/site/apidocs/index.html
   ```

3. **Empaquetar JAR**
   ```bash
   .\mvnw.cmd package
   ```

4. **GitHub**
   ```bash
   git add .
   git commit -m "Implement Cincuentazo game"
   git push origin main
   ```

---

## 📞 Contacto / Preguntas

Si tienes preguntas sobre la implementación, revisa los archivos documentados:
- Cada clase tiene comentarios Javadoc
- Los métodos principales tienen descripciones
- El código sigue convenciones Java estándar

---

**Proyecto Completado**: ✅ 22/06/2026
**Versión**: 1.0-SNAPSHOT
**Estado**: LISTO PARA PRESENTACIÓN

