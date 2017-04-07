package pkg2d.scroller.pkg2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author AveryVine
 */
public class Wall extends Sprite {
    
    private final boolean sticky;
    
    public Wall(int xCoordinate, int yCoordinate, int xLength, int yLength, boolean sticky) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.xLength = xLength;
        this.yLength = yLength;
        this.sticky = sticky;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.fillRect(xCoordinate, yCoordinate, xLength, yLength);
        if (sticky) {
            g2d.setPaint(Color.GRAY);
            g2d.fillRect(xCoordinate, yCoordinate, xLength, yLength);
            g2d.setPaint(Color.BLACK);
            g2d.drawRect(xCoordinate, yCoordinate, xLength, yLength);
        }
    }
    
//    @Override
//    public boolean intersects(Sprite sprite) {
//        
//    }
    
}
