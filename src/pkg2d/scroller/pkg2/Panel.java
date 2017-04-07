package pkg2d.scroller.pkg2;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;

/**
 *
 * @author AveryVine
 */
public class Panel extends JPanel {
    private final Resources resources;
    private final Character character;
    private final int frames, ticks;
    
    private GameLevel level;
    private int currentLevel, time;
    private boolean leftKey, rightKey;
    
    
    public Panel() {
        addKeyListener(new Listener());
        setFocusable(true);
        resources = new Resources();
        frames = 1000 / Scroller2.FRAMES_PER_SECOND;
        ticks = 1000 / Scroller2.TICKS_PER_SECOND;
        currentLevel = -1;
        time = 0;
        startMusic();
        character = new Character();
        incrementLevel();
    }
    
    public void gameLoop() {
        javax.swing.Timer drawTimer = new javax.swing.Timer(frames, (ActionEvent e) -> {
            repaint();
        });
        drawTimer.start();
        
        java.util.Timer gameTimer = new java.util.Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentLevel > 0) {
//                    boolean intersects = false;
//                    Character tempCharacter = new Character(character);
//                    moveCharacter(tempCharacter);
//                    level.walls.forEach((wall) -> {
//                        if (wall.intersects(tempCharacter)) {
//                            intersects = true;
//                        }
//                    });
                    
                    moveCharacter(character);
                    
                    if (character.intersects(level.flag)) {
                        incrementLevel();
                    }
                }
            }
            
        }, 0, ticks);
    }
    
    private void moveCharacter(Character character) {
        if (leftKey) {
            character.move(-1);
        }
        else if (rightKey) {
            character.move(1);
        }
        else {
            character.move(0);
        }
    }
    
    private void startMusic() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(resources.MUSIC);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void incrementLevel() {
        if (currentLevel > 0) {
            time += level.getTime();
        }
        currentLevel++;
        level = new GameLevel(currentLevel);
        character.spawn(level.getCharacterSpawn("x"), level.getCharacterSpawn("y"));
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
        g.drawImage(resources.BACKGROUND_IMAGE, 0, 0, this);
        level.paint(g);
        character.paint(g);
    }
    
    private class Listener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (currentLevel == 0) {
                incrementLevel();
            }
            else {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        leftKey = false;
                        if (!rightKey) {
                            character.moving = false;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightKey = false;
                        if (!leftKey) {
                            character.moving = false;
                        }
                        break;
                }
            }
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (currentLevel > 0) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        leftKey = true;
                        character.facingRight = false;
                        character.moving = true;
                        if (rightKey) {
                            rightKey = !rightKey;
                        }
                        level.hideText();
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightKey = true;
                        character.facingRight = true;
                        character.moving = true;
                        if (leftKey) {
                            leftKey = !leftKey;
                        }
                        level.hideText();
                        break;
                    case KeyEvent.VK_UP:
                        if (!character.isAirborne()) {
                            character.boost();
                        }
                }
            }
        }
    }
}
