import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Snake class - represents the multi-segment enemy snake
 * Inherits from GameObject but adds complex multi-segment behavior
 */
public class Snake extends GameObject {
    private ArrayList<Point> segments;  // All snake body segments
    private Random random;
    private double huntingChance = 0.63;  // 70% chance to move toward player
    
    public Snake(int startX, int startY, int segmentCount) {
        super(startX, startY, Color.RED.darker());
        this.visible = false;  // Snake is hidden from player view
        this.segments = new ArrayList<>();
        this.random = new Random();
        
        // Initialize snake segments
        initializeSegments(startX, startY, segmentCount);
    }
    
    private void initializeSegments(int headX, int headY, int count) {
        segments.clear();
        segments.add(new Point(headX, headY)); // Head
        
        // Add body segments extending leftward (or rightward if can't go left)
        for (int i = 1; i < count; i++) {
            int segX = headX - i;
            if (segX < 0) segX = headX + i;  // Go right if can't go left
            segments.add(new Point(segX, headY));
        }
        
        // Update our position to match the head
        this.x = headX;
        this.y = headY;
    }
    
    @Override
    public void draw(Graphics g, int tileSize) {
        if (!visible) return;
        
        g.setColor(color);
        for (int i = 0; i < segments.size(); i++) {
            Point segment = segments.get(i);
            int pixelX = segment.x * tileSize;
            int pixelY = segment.y * tileSize;
            
            // Head is slightly larger
            int size = (i == 0) ? tileSize - 6 : tileSize - 8;
            int offset = (i == 0) ? 3 : 4;
            
            g.fillOval(pixelX + offset, pixelY + offset, size, size);
            g.setColor(Color.BLACK);
            g.drawOval(pixelX + offset, pixelY + offset, size, size);
            g.setColor(color);
        }
    }
    

    
    /**
     * Move the snake toward the player with some randomness
     */
    public void moveToward(Player player, int gridWidth, int gridHeight) {
        if (segments.isEmpty()) return;
        
        Point head = segments.get(0);
        int newX = head.x;
        int newY = head.y;
        
        // Snake AI: mostly hunt player, sometimes move randomly
        if (random.nextDouble() < huntingChance) {
            // Move toward player intelligently
            int deltaX = player.getX() - head.x;
            int deltaY = player.getY() - head.y;
            
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                // Move horizontally toward player
                newX += (deltaX > 0) ? 1 : -1;
            } else {
                // Move vertically toward player  
                newY += (deltaY > 0) ? 1 : -1;
            }
        } else {
            // Random movement for unpredictability
            int direction = random.nextInt(4);
            switch (direction) {
                case 0: newY--; break; // Up
                case 1: newY++; break; // Down
                case 2: newX--; break; // Left
                case 3: newX++; break; // Right
            }
        }
        
        // Only move if valid and not into itself
        if (isValidPosition(newX, newY, gridWidth, gridHeight) && 
            !isSnakePosition(newX, newY)) {
            
            // Move snake: add new head, remove tail
            segments.add(0, new Point(newX, newY));
            segments.remove(segments.size() - 1);
            
            // Update our position to match the head
            this.x = newX;
            this.y = newY;
        }
    }
    
    /**
     * Check if the snake collides with the player
     */
    public boolean collidesWith(Player player) {
        for (Point segment : segments) {
            if (segment.x == player.getX() && segment.y == player.getY()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if a position is occupied by any snake segment
     */
    public boolean isSnakePosition(int x, int y) {
        for (Point segment : segments) {
            if (segment.x == x && segment.y == y) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get distance to closest snake segment from a position
     */
    public int getClosestDistanceTo(int x, int y) {
        int minDistance = Integer.MAX_VALUE;
        for (Point segment : segments) {
            int distance = Math.abs(x - segment.x) + Math.abs(y - segment.y);
            minDistance = Math.min(minDistance, distance);
        }
        return minDistance;
    }
    

    
    private boolean isValidPosition(int x, int y, int gridWidth, int gridHeight) {
        return x >= 0 && x < gridWidth && y >= 0 && y < gridHeight;
    }
    
    public int getSegmentCount() {
        return segments.size();
    }
}