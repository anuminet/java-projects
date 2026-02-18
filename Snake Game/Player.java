import java.awt.*;

/**
 * Player class - represents the player character
 * Inherits from GameObject, adding player-specific behavior
 */
public class Player extends GameObject {
    
    public Player(int startX, int startY) {
        super(startX, startY, Color.ORANGE);  // Call parent constructor
        this.visible = true;  // Player is always visible
    }
    
    @Override
    public void draw(Graphics g, int tileSize) {
        if (!visible) return;
        
        int pixelX = x * tileSize;
        int pixelY = y * tileSize;
        
        // Draw the player as an orange circle with white border
        g.setColor(color);
        g.fillOval(pixelX + 5, pixelY + 5, tileSize - 10, tileSize - 10);
        
        // White border for visibility
        g.setColor(Color.WHITE);
        g.drawOval(pixelX + 5, pixelY + 5, tileSize - 10, tileSize - 10);
        
        // Draw "P" in the center
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int stringX = pixelX + (tileSize - fm.stringWidth("P")) / 2;
        int stringY = pixelY + (tileSize + fm.getAscent()) / 2;
        g.drawString("P", stringX, stringY);
    }
    

    
    /**
     * Move the player to a new position
     * Returns true if the move was successful
     */
    public boolean moveTo(int newX, int newY, int gridWidth, int gridHeight) {
        // Check bounds
        if (newX >= 0 && newX < gridWidth && newY >= 0 && newY < gridHeight) {
            setPosition(newX, newY);
            return true;
        }
        return false;
    }
    
    /**
     * Get the direction the player should move based on key input
     */
    public static Point getMovementDirection(int keyCode) {
        switch (keyCode) {
            case java.awt.event.KeyEvent.VK_UP:    return new Point(0, -1);
            case java.awt.event.KeyEvent.VK_DOWN:  return new Point(0, 1);
            case java.awt.event.KeyEvent.VK_LEFT:  return new Point(-1, 0);
            case java.awt.event.KeyEvent.VK_RIGHT: return new Point(1, 0);
            default: return new Point(0, 0);  // No movement
        }
    }
}