package pkg2d.scroller.pkg2;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author AveryVine
 */
public class Character extends Sprite implements Gravity {
    
    private final Image leftSprite[], rightSprite[];
    private final int spriteChangesPerSecond;
    private final double moveSpeed;
    
    private int currentFrame, currentSprite;
    private double velocity, xCoordinatePrecise;
    private boolean isAirborne;
    
    public boolean facingRight, moving;
    
    
    public Character() {
        currentFrame = 0;
        currentSprite = 0;
        velocity = 0.0;
        spriteChangesPerSecond = 10;
        moveSpeed = 1.5;
        isAirborne = false;
        facingRight = true;
        moving = false;
        leftSprite = new Image[4];
        rightSprite = new Image[4];
        leftSprite[0] = new ImageIcon(getClass().getClassLoader().getResource("resources/CharacterLeftStand.png")).getImage();
        leftSprite[1] = new ImageIcon(getClass().getClassLoader().getResource("resources/CharacterLeftWalk1.png")).getImage();
        leftSprite[2] = leftSprite[0];
        leftSprite[3] = new ImageIcon(getClass().getClassLoader().getResource("resources/CharacterLeftWalk2.png")).getImage();
        rightSprite[0] = new ImageIcon(getClass().getClassLoader().getResource("resources/CharacterRightStand.png")).getImage();
        rightSprite[1] = new ImageIcon(getClass().getClassLoader().getResource("resources/CharacterRightWalk1.png")).getImage();
        rightSprite[2] = rightSprite[0];
        rightSprite[3] = new ImageIcon(getClass().getClassLoader().getResource("resources/CharacterRightWalk2.png")).getImage();
        xLength = leftSprite[0].getWidth(this);
        yLength = leftSprite[0].getHeight(this);
    }
    
//    public Character(Character character) {
//        xCoordinatePrecise = character.xCoordinatePrecise;
//        xCoordinate = character.xCoordinate;
//        yCoordinate = character.yCoordinate;
//        moveSpeed = 1.5;
//        xLength = character.xLength;
//        leftSprite = null;
//        rightSprite = null;
//        spriteChangesPerSecond = 0;
//    }
    
    public void move(int dx) {
        int dy = getVerticalTick();
        xCoordinatePrecise += (moveSpeed * dx);
        xCoordinate = (int)xCoordinatePrecise;
        yCoordinate += dy;
        if (xCoordinate < 0) {
            xCoordinate = 0;
        }
        if (xCoordinate + xLength > Scroller2.DIMENSIONS.width) {
            xCoordinate = Scroller2.DIMENSIONS.width - xLength;
        }
    }
    
    @Override
    public void spawn(int x, int y) {
        super.spawn(x, y);
        xCoordinatePrecise = x;
    }
    
    @Override
    public void boost() {
        velocity = INITIAL_VELOCITY;
        isAirborne = true;
    }
    
    @Override
    public int getVerticalTick() {
        double dy = 0.0;
        if (isAirborne()) {
            dy = ACCELERATION * (double)(1 / (double)Scroller2.TICKS_PER_SECOND);
        }
        velocity += dy;
        return (int) velocity;
    }
    
    @Override
    public boolean isAirborne() {
        return isAirborne;
    }
    
    @Override
    public Image getSprite() {
        Image sprite;
        if (moving) {
            if (currentFrame % (Scroller2.FRAMES_PER_SECOND / spriteChangesPerSecond) == 0) {
                currentSprite = (currentSprite + 1) % 4;
            }
            currentFrame++;
        }
        else {
            currentSprite = 0;
            currentFrame = 0;
        }
        if (facingRight) {
            sprite = rightSprite[currentSprite];
        }
        else {
            sprite = leftSprite[currentSprite];
        }
        return sprite;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(getSprite(), xCoordinate, yCoordinate, this);
    }

}
