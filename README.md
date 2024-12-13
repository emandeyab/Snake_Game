# Snake Game

This is a simple Snake game built using JavaFX. The game allows the player to control a snake, eat food to grow longer, and avoid traps. The game ends when the snake collides with itself. The game also features a speed increase as the snake eats more food and decreases when it hits a trap.

## Features

- **Snake Movement**: The player can control the snake using arrow keys (Up, Down, Left, Right).
- **Food**: The snake eats food to grow longer, and each piece of food increases the game speed.
- **Traps**: Traps randomly appear on the grid. If the snake hits a trap, it loses a segment of its body and the game speed decreases.
- **No Borders**: The game features an endless grid with no boundaries, meaning the snake can move off one edge of the screen and appear on the opposite side.
- **Grid and Cell Size**: The game is played on a 25x25 grid, and the size of each cell is 20px.
- **Game Over**: The game ends when the snake collides with its own body.

## Requirements

- Java 17 or later
- JavaFX (included in the JDK if using OpenJDK with JavaFX modules)

## How to Run

1. Clone the repository or download the project.
2. Make sure you have Java 17 or later installed.
3. Ensure JavaFX is set up correctly in your IDE or build tool (e.g., IntelliJ IDEA or Eclipse).
4. Run the `Snake.java` class from the `org.example.snake` package.
5. Press the **Start** button to begin playing.
6. Use the arrow keys to control the snake's movement.
7. The game will end when the snake collides with itself.

## Game Controls

- **Up Arrow**: Move the snake up.
- **Down Arrow**: Move the snake down.
- **Left Arrow**: Move the snake left.
- **Right Arrow**: Move the snake right.

## How the Game Works

- The game begins with a small snake of 3 segments in the center of the grid.
- Food appears randomly on the grid. When the snake eats the food, it grows longer, and the game speed increases.
- Traps appear randomly. If the snake hits a trap, it loses a segment, and the game speed decreases. If the snake has only one segment left, hitting a trap results in a game over.
- The game features **no borders**: when the snake moves off one edge of the screen, it reappears on the opposite edge, allowing continuous movement without boundaries.
- The game ends when the snake collides with itself.

## Customization

- You can modify the grid dimensions, snake speed, and other game parameters in the `Snake` class.
