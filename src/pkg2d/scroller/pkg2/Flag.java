package pkg2d.scroller.pkg2;

import java.awt.Graphics;

/**
 *
 * @author AveryVine
 */
public class Flag extends Sprite {
    
    public Flag(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(getSprite(), yCoordinate, xCoordinate, this);
    }
}
