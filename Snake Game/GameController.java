import javax.swing.*;
import java.util.Random;

/**
 * GameController - manages all game logic and entity interactions
 * This demonstrates composition - the controller HAS entities rather than IS an entity
 */
public class GameController {
    // Game settings
    public static final int GRID_WIDTH = 12;
    public static final int GRID_HEIGHT = 10;
    
    // Game entities (composition - the controller "has" these objects)
    private Player player;
    private Snake snake;
    private Exit exit;
    
    // Game state
    private boolean gameOver = false;
    private boolean escaped = false;
    private int moveCount = 0;
    private int lastHintMove = -5;
    private Random random;
    
    public GameController() {
        this.random = new Random();
        initializeGame();
    }
    
    /**
     * Initialize or reset the game
     */
    public void initializeGame() {
        // Create player at random position
        int playerX = random.nextInt(GRID_WIDTH);
        int playerY = random.nextInt(GRID_HEIGHT);
        player = new Player(playerX, playerY);
        
        // Create snake away from player
        int snakeX, snakeY;
        do {
            snakeX = random.nextInt(GRID_WIDTH);
            snakeY = random.nextInt(GRID_HEIGHT);
        } while (Math.abs(snakeX - playerX) < 3 || Math.abs(snakeY - playerY) < 3);
        
        snake = new Snake(snakeX, snakeY, 3); // 3 segments
        
        // Create exit away from both player and snake
        int exitX, exitY;
        do {
            exitX = random.nextInt(GRID_WIDTH);
            exitY = random.nextInt(GRID_HEIGHT);
        } while ((exitX == playerX && exitY == playerY) || 
                 snake.isSnakePosition(exitX, exitY));
        
        exit = new Exit(exitX, exitY);
        
        // Reset game state
        gameOver = false;
        escaped = false;
        moveCount = 0;
        lastHintMove = -5;
    }
    
    /**
     * Process a player move based on key input
     */
    public boolean processPlayerMove(int keyCode) {
        if (gameOver) {
            return false;
        }
        
        // Get movement direction from Player class
        java.awt.Point direction = Player.getMovementDirection(keyCode);
        
        if (direction.x == 0 && direction.y == 0) {
            return false; // No valid movement
        }
        
        // Calculate new position
        int newX = player.getX() + direction.x;
        int newY = player.getY() + direction.y;
        
        // Try to move player
        if (player.moveTo(newX, newY, GRID_WIDTH, GRID_HEIGHT)) {
            moveCount++;
            
            // Update game state after successful move
            updateGameState();
            return true;
        }
        
        return false;
    }
    
    /**
     * Update game state after each move
     */
    private void updateGameState() {
        // Move the snake
        snake.moveToward(player, GRID_WIDTH, GRID_HEIGHT);
        
        // Check win condition
        if (exit.isReachedBy(player)) {
            escaped = true;
            gameOver = true;
            return;
        }
        
        // Check lose condition
        if (snake.collidesWith(player)) {
            escaped = false;
            gameOver = true;
            return;
        }
        
        // Show hints every 3 moves
        if (moveCount - lastHintMove >= 3) {
            showProximityHint();
            lastHintMove = moveCount;
        }
    }
    
    /**
     * Show popup hints about exit location and snake proximity
     */
    private void showProximityHint() {
        // Get exit information
        int exitDistance = exit.getDistanceTo(player.getX(), player.getY());
        String exitDirection = exit.getDirectionFrom(player.getX(), player.getY());
        String distanceHint = exit.getDistanceDescription(exitDistance);
        
        // Get snake proximity warning
        int snakeDistance = snake.getClosestDistanceTo(player.getX(), player.getY());
        String snakeWarning = getSnakeWarning(snakeDistance);
        
        // Create the hint message
        String message = "\"" + distanceHint + " Try heading " + exitDirection + "!\"" + snakeWarning;
        
        JOptionPane.showMessageDialog(null, message, "Voice from Above", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Get snake proximity warning message
     */
    private String getSnakeWarning(int distance) {
        if (distance <= 2) {
            return "\n WARNING: The snake is VERY close!";
        } else if (distance <= 4) {
            return "\n You sense movement nearby...";
        }
        return "";
    }
    
    /**
     * Handle game restart
     */
    public void restartGame() {
        initializeGame();
    }
    
    // Getters for the UI to access game entities and state
    public Player getPlayer() { return player; }
    public Snake getSnake() { return snake; }
    public Exit getExit() { return exit; }
    
    public boolean isGameOver() { return gameOver; }
    public boolean hasEscaped() { return escaped; }
    public int getMoveCount() { return moveCount; }
    
    // Game configuration getters
    public int getGridWidth() { return GRID_WIDTH; }
    public int getGridHeight() { return GRID_HEIGHT; }
    
    /**
     * Get game status message for display
     */
    public String getStatusMessage() {
        return "Moves: " + moveCount + " | Snake has " + snake.getSegmentCount() + " segments in the horizontal or vertical direction";
    }
    
    /**
     * Get game over message
     */
    public String getGameOverMessage() {
        if (escaped) {
            return " You found the trapdoor and escaped to freedom! ";
        } else {
            return " You have been eaten by the snake! ";
        }
    }
    
    /**
     * Enable debug mode to make hidden entities visible
     */
    public void setDebugMode(boolean debug) {
        snake.setVisible(debug);
        exit.setVisible(debug);
    }
}