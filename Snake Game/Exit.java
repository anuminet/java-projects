import java.awt.*;

/**
 * Exit class - represents the hidden exit trapdoor
 * Inherits from GameObject but is invisible to the player
 */
public class Exit extends GameObject {
    
    public Exit(int exitX, int exitY) {
        super(exitX, exitY, Color.GREEN);
        this.visible = false;  // Exit is hidden from player view
    }
    
    @Override
    public void draw(Graphics g, int tileSize) {
        if (!visible) return;
        
        int pixelX = x * tileSize;
        int pixelY = y * tileSize;
        
        g.setColor(color);
        g.fillRect(pixelX + 8, pixelY + 8, tileSize - 16, tileSize - 16);
        g.setColor(Color.BLACK);
        g.drawRect(pixelX + 8, pixelY + 8, tileSize - 16, tileSize - 16);
        
        // Draw "E" in the center
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();
        int stringX = pixelX + (tileSize - fm.stringWidth("E")) / 2;
        int stringY = pixelY + (tileSize + fm.getAscent()) / 2;
        g.drawString("E", stringX, stringY);
    }
    

    
    /**
     * Check if the player has reached the exit
     */
    public boolean isReachedBy(Player player) {
        return player.getX() == this.x && player.getY() == this.y;
    }
    
    /**
     * Get the Manhattan distance from the exit to a position
     */
    public int getDistanceTo(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }
    
    /**
     * Get a directional hint toward the exit from a given position
     */
    public String getDirectionFrom(int fromX, int fromY) {
        String direction = "";
        
        if (this.x > fromX) direction += "East ";
        else if (this.x < fromX) direction += "West ";
        
        if (this.y > fromY) direction += "South";
        else if (this.y < fromY) direction += "North";
        
        return direction.trim();
    }
    
    /**
     * Get a distance description for hints
     */
    public String getDistanceDescription(int distance) {
        if (distance <= 3) {
            return " The exit is VERY close!";
        } else if (distance <= 6) {
            return " The exit is nearby.";
        } else {
            return " The exit feels distant.";
        }
    }
    

}