import java.awt.*;

/**
 * Abstract base class for all game entities (Player, Snake, Exit)
 * This demonstrates inheritance - all game objects share common properties
 */
public abstract class GameObject {
    protected int x, y;           // Position on the grid
    protected Color color;        // Color to draw this object
    protected boolean visible;    // Whether this object should be drawn
    
    public GameObject(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.visible = true;
    }
    
    // Abstract method - each subclass must implement their own drawing logic
    public abstract void draw(Graphics g, int tileSize);
    

    
    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) { 
        this.x = x; 
        this.y = y; 
    }
    
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    
    // Utility method for collision detection
    public boolean isAtPosition(int checkX, int checkY) {
        return this.x == checkX && this.y == checkY;
    }
}