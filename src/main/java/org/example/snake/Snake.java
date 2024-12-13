package org.example.snake;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class Snake extends Application {
    static int gameSpeed = 5;
    static int gridWidth = 25;
    static int gridHeight = 25;
    static int foodPositionX = 0;
    static int foodPositionY = 0;
    static int trapPositionX = 0;
    static int trapPositionY = 0;
    static int cellSize = 20;
    static List<Segment> snakeBody = new ArrayList<>();
    static Direction currentDirection = Direction.left;
    static boolean isGameOver = false;
    static boolean isGameRunning = false;
    static Random random = new Random();

    public enum Direction {
        left, right, up, down
    }

    public static class Segment {
        int x;
        int y;

        public Segment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void start(Stage primaryStage) {
        try {
            // Create the canvas and graphics context
            Canvas gameCanvas = new Canvas(gridWidth * cellSize, gridHeight * cellSize);
            GraphicsContext gc = gameCanvas.getGraphicsContext2D();

            // Create a Start button
            Button startGameButton = new Button("Start");
            startGameButton.setOnAction(e -> {
                isGameRunning = true;
                startGame(gc); // Start the game and pass the graphics context to draw
                startGameButton.setVisible(false);
            });

            VBox rootLayout = new VBox();
            rootLayout.getChildren().add(startGameButton);
            rootLayout.getChildren().add(gameCanvas);

            Scene gameScene = new Scene(rootLayout, gridWidth * cellSize, gridHeight * cellSize);
            gameScene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (isGameRunning) {
                    switch (key.getCode()) {
                        case UP -> { if (currentDirection != Direction.down) currentDirection = Direction.up; }
                        case LEFT -> { if (currentDirection != Direction.right) currentDirection = Direction.left; }
                        case DOWN -> { if (currentDirection != Direction.up) currentDirection = Direction.down; }
                        case RIGHT -> { if (currentDirection != Direction.left) currentDirection = Direction.right; }
                    }
                }
            });

            primaryStage.setScene(gameScene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void startGame(GraphicsContext gc) {
        generateFood();
        generateTrap();
        snakeBody.clear();
        snakeBody.add(new Segment(gridWidth / 2, gridHeight / 2));
        snakeBody.add(new Segment(gridWidth / 2, gridHeight / 2));
        snakeBody.add(new Segment(gridWidth / 2, gridHeight / 2));
        isGameOver = false;
        gameSpeed = 5;
        currentDirection = Direction.left;

        new AnimationTimer() {
            long lastTickTime = 0;

            public void handle(long currentTime) {
                if (lastTickTime == 0) {
                    lastTickTime = currentTime;
                    gameTick(gc);
                    return;
                }

                if (currentTime - lastTickTime > 1000000000 / gameSpeed) {
                    lastTickTime = currentTime;
                    gameTick(gc);
                }
            }
        }.start();
    }
   public static void gameTick(GraphicsContext gc) {
       if (isGameOver) {
           // Handle game over screen
           gc.setFill(Color.BLACK);
           gc.setFont(new Font(50));
           gc.fillText("GAME OVER", gridWidth * cellSize / 4, gridHeight * cellSize / 2);
           return;
       }
       gc.clearRect(0, 0, gridWidth * cellSize, gridHeight * cellSize);
       // draw background
       gc.setFill(Color.rgb(217, 201, 159));
       gc.fillRect(0, 0, gridWidth * cellSize, gridHeight * cellSize);

       for (int i = snakeBody.size() - 1; i >= 1; i--) {
           snakeBody.get(i).x = snakeBody.get(i - 1).x;
           snakeBody.get(i).y = snakeBody.get(i - 1).y;
       }

       switch (currentDirection) {
           case up:
               snakeBody.get(0).y--;
               if (snakeBody.get(0).y < 0) snakeBody.get(0).y = gridHeight - 1;
               break;
           case down:
               snakeBody.get(0).y++;
               if (snakeBody.get(0).y >= gridHeight) snakeBody.get(0).y = 0;
               break;
           case left:
               snakeBody.get(0).x--;
               if (snakeBody.get(0).x < 0) snakeBody.get(0).x = gridWidth - 1;
               break;
           case right:
               snakeBody.get(0).x++;
               if (snakeBody.get(0).x >= gridWidth) snakeBody.get(0).x = 0;
               break;
       }

       if (foodPositionX == snakeBody.get(0).x && foodPositionY == snakeBody.get(0).y) {
           snakeBody.add(new Segment(-1, -1));
           gameSpeed++;
           generateFood();
       }

       if (trapPositionX == snakeBody.get(0).x && trapPositionY == snakeBody.get(0).y) {
           if (snakeBody.size() > 1) {
               snakeBody.remove(snakeBody.size() - 1);
               gameSpeed--;
           } else {
               isGameOver = true;
           }
           generateTrap();
       }

       for (int i = 1; i < snakeBody.size(); i++) {
           if (snakeBody.get(0).x == snakeBody.get(i).x && snakeBody.get(0).y == snakeBody.get(i).y) {
               isGameOver = true;
           }
       }

       // draw the snake
       gc.setFill(Color.BLACK);
       for (Segment s : snakeBody) {
           gc.fillRect(s.x * cellSize, s.y * cellSize, cellSize, cellSize);
       }

       // draw the food
       gc.setFill(Color.GREEN);
       gc.fillRect(foodPositionX * cellSize, foodPositionY * cellSize, cellSize, cellSize);

       // draw the trap
       gc.setFill(Color.RED);
       gc.fillRect(trapPositionX * cellSize, trapPositionY * cellSize, cellSize, cellSize);

       gc.setFill(Color.BLACK);
       gc.setFont(new Font(20));
       gc.fillText("Score: " + (snakeBody.size() - 3), 10, 20);
   }

    public static void generateFood() {
        start: while (true) {
            foodPositionX = random.nextInt(gridWidth);
            foodPositionY = random.nextInt(gridHeight);

            for (Segment s : snakeBody) {
                if (s.x == foodPositionX && s.y == foodPositionY) {
                    continue start;
                }
            }
            break;
        }
    }

    public static void generateTrap() {
        start: while (true) {
            trapPositionX = random.nextInt(gridWidth);
            trapPositionY = random.nextInt(gridHeight);

            for (Segment s : snakeBody) {
                if (s.x == trapPositionX && s.y == trapPositionY) {
                    continue start;
                }
            }
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
