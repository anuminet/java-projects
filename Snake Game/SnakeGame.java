import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * SnakeGameGUI - The main game window and UI controller
 * Now much cleaner - only handles display and user input!
 * Uses composition to delegate game logic to GameController
 */
public class SnakeGame extends JPanel implements KeyListener {
    private static final int TILE_SIZE = 50;
    
    // Colors for the game environment
    private static final Color FLOOR_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = new Color(40, 40, 40);
    
    // Game controller handles all the logic (composition pattern)
    private GameController gameController;
    
    public SnakeGame() {
        // Initialize the game controller
        gameController = new GameController();
        
        // Set up the UI
        int windowWidth = gameController.getGridWidth() * TILE_SIZE;
        int windowHeight = gameController.getGridHeight() * TILE_SIZE + 100;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        // Show intro message
        showIntroMessage();
    }
    
    private void showIntroMessage() {
        String message = "Welcome to the Snake Den!\n\n" +
                        "You awaken in complete darkness, lost and afraid.\n" +
                        "The voice from above whispers: \"You must find the exit trapdoor!\"\n" +
                        "\"The snake cannot see you, but it senses your movement...\"\n" +
                        "\"I will guide you with whispers - listen carefully!\"\n\n" +
                        "Controls:\n" +
                        "Arrow Keys = Move one tile at a time\n" +
                        "Orange Circle = You (the player)\n" +
                        "D = Toggle debug mode (show hidden entities)\n\n" +
                        "The snake and exit are completely HIDDEN.\n" +
                        "Every 3 moves, listen for guidance hints!\n" +
                        "Move carefully... the snake is hunting you.\n\n" +
                        "Press OK to enter the darkness...";
        
        JOptionPane.showMessageDialog(this, message, "The Snake Lair", JOptionPane.WARNING_MESSAGE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (gameController.isGameOver()) {
            drawGameOverScreen(g);
            return;
        }
        
        // Draw the game world
        drawFloor(g);
        drawEntities(g);
        drawUI(g);
    }
    
    /**
     * Draw the tiled floor
     */
    private void drawFloor(Graphics g) {
        int gridWidth = gameController.getGridWidth();
        int gridHeight = gameController.getGridHeight();
        
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                int pixelX = x * TILE_SIZE;
                int pixelY = y * TILE_SIZE;
                
                // Draw dark floor tile
                g.setColor(FLOOR_COLOR);
                g.fillRect(pixelX, pixelY, TILE_SIZE, TILE_SIZE);
                
                // Draw subtle grid lines
                g.setColor(WALL_COLOR);
                g.drawRect(pixelX, pixelY, TILE_SIZE, TILE_SIZE);
            }
        }
    }
    
    /**
     * Draw all game entities using polymorphism
     */
    private void drawEntities(Graphics g) {
        // This is the beauty of inheritance - we can treat all game objects the same way!
        GameObject[] entities = {
            gameController.getPlayer(),
            gameController.getSnake(),
            gameController.getExit()
        };
        
        // Polymorphism in action - each object draws itself correctly
        for (GameObject entity : entities) {
            entity.draw(g, TILE_SIZE);
        }
    }
    
    /**
     * Draw UI information
     */
    private void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        int uiY = gameController.getGridHeight() * TILE_SIZE + 20;
        
        g.drawString("Find the hidden EXIT and avoid the invisible SNAKE!", 10, uiY);
        g.drawString("Use Arrow Keys to move - Listen for guidance hints!", 10, uiY + 25);
        g.drawString(gameController.getStatusMessage(), 10, uiY + 50);
    }
    
    /**
     * Draw game over screen
     */
    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Main message
        g.setColor(gameController.hasEscaped() ? Color.GREEN : Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 38));
        FontMetrics fm = g.getFontMetrics();
        
        String title = gameController.hasEscaped() ? " YOU ESCAPED! " : " YOU DIED! ";
        int x = (getWidth() - fm.stringWidth(title)) / 2;
        int y = getHeight() / 2 - 50;
        g.drawString(title, x, y);
        
        // Subtitle
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        fm = g.getFontMetrics();
        
        String subtitle = gameController.getGameOverMessage();
        x = (getWidth() - fm.stringWidth(subtitle)) / 2;
        g.drawString(subtitle, x, y + 60);
        
        String instructions = "Press SPACE to play again or ESC to quit";
        x = (getWidth() - fm.stringWidth(instructions)) / 2;
        g.drawString(instructions, x, y + 100);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameController.isGameOver()) {
            handleGameOverInput(e);
        } else {
            handleGameInput(e);
        }
    }
    
    /**
     * Handle input during gameplay
     */
    private void handleGameInput(KeyEvent e) {
        // Handle debug mode toggle
        if (e.getKeyCode() == KeyEvent.VK_D) {
            // Toggle debug mode (this will make hidden entities visible)
            gameController.setDebugMode(!gameController.getSnake().isVisible());
            repaint();
            return;
        }
        
        // Process player movement through the controller
        if (gameController.processPlayerMove(e.getKeyCode())) {
            repaint(); // Redraw if the move was successful
        }
    }
    
    /**
     * Handle input when game is over
     */
    private void handleGameOverInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameController.restartGame();
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
    
    /**
     * Main method to run the game
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(" The Snake's Lair ");
        SnakeGame game = new SnakeGame();
        
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        game.requestFocus();
    }
}