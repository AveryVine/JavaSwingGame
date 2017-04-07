package pkg2d.scroller.pkg2;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 *
 * @author AveryVine
 */
public class Resources {
    public final Image BACKGROUND_IMAGE;
    public AudioInputStream MUSIC;
    
    public Resources() {
        BACKGROUND_IMAGE = new ImageIcon(getClass().getClassLoader().getResource("resources/Background.png")).getImage();
        try {
            MUSIC = AudioSystem.getAudioInputStream(getClass().getResource("/resources/BackgroundMusic.wav"));
        } catch (UnsupportedAudioFileException | IOException ex) {
            MUSIC = null;
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
