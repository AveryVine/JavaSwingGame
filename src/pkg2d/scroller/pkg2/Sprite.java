package pkg2d.scroller.pkg2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 *
 * @author AveryVine
 */
public abstract class Sprite implements ImageObserver, Intersectable {
    
    protected int xCoordinate, yCoordinate, xLength, yLength;
    private final Image image;
    
    public Sprite() {
        this("Flag");
    }
    
    public Sprite(String imageStr) {
        xCoordinate = 0;
        yCoordinate = 0;
        image = new ImageIcon(getClass().getClassLoader().getResource("resources/" + imageStr + ".png")).getImage();
        xLength = image.getWidth(this);
        yLength = image.getHeight(this);
    }
    
    public abstract void paint(Graphics g);
    
    protected Rectangle getBounds() {
        if (xCoordinate < 0 || yCoordinate < 0 || xLength < 0 || yLength < 0) {
            xCoordinate = 0;
            yCoordinate = 0;
            xLength = 0;
            yLength = 0;
        }
        return new Rectangle(xCoordinate, yCoordinate, xLength, yLength);
    }
    
    public void spawn(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }
    
    public Image getSprite() {
        return image;
    }
    
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        throw new UnsupportedOperationException("imageUpdate is not support, what the heck are you doing???");
    }
    
    @Override
    public boolean intersects(Sprite sprite) {
        if (sprite != null) {
            Rectangle self = getBounds();
            Rectangle spriteRect = sprite.getBounds();
            if (self != null && spriteRect != null) {
                return self.intersects(spriteRect);
            }
        }
        return false;
    }
    
}
