package pkg2d.scroller.pkg2;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author AveryVine
 */
public class Scroller2 {
    
    public static Dimension DIMENSIONS;
    public static int FRAMES_PER_SECOND = 60;
    public static int TICKS_PER_SECOND = 144;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Panel panel = new Panel();
        frame.add(panel);
        frame.setSize(950, 800);
        DIMENSIONS = frame.getSize();
        frame.setResizable(false);
        frame.setTitle("Jimmy's Adventures");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.gameLoop();
    }
    
}
