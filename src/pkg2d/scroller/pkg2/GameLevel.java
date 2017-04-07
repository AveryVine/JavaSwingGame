package pkg2d.scroller.pkg2;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author AveryVine
 */
public class GameLevel {
    
    private final int level;
    
    private String scrollingText, largeText, mediumText, smallText;
    private int characterSpawnX, characterSpawnY, scrollTick, time;
    private boolean hideText;
    
    public final ArrayList<Wall> walls;
    
    public Flag flag;
    
    public GameLevel(int level) {
        this.level = level;
        walls = new ArrayList<>();
        flag = null;
        scrollingText = null;
        largeText = null;
        mediumText = null;
        smallText = null;
        characterSpawnX = 1000;
        characterSpawnY = 1000;
        scrollTick = 0;
        time = 0;
        hideText = true;
        loadContentsFromFile();
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time++;
            }
        }, 1000, 1000);
    }
    
    public void paint(Graphics g) {
        if (flag != null) {
            flag.paint(g);
        }
        walls.forEach((wall) -> {
            wall.paint(g);
        });
        if (scrollingText != null) {
            drawText(g, Scroller2.DIMENSIONS, scrollingText, "SCROLLING");
        }
        if (largeText != null && !hideText) {
            drawText(g, Scroller2.DIMENSIONS, largeText, "LARGE");
        }
        if (mediumText != null && !hideText) {
            drawText(g, Scroller2.DIMENSIONS, mediumText, "MEDIUM");
        }
        if (smallText != null && !hideText) {
            drawText(g, Scroller2.DIMENSIONS, smallText, "SMALL");
        }
    }
    
    public void hideText() {
        if (time < 2) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    hideText = true;
                }
            }, 2000 - (1000 * time));
        }
        else {
            hideText = true;
        }
    }
    
    public int getTime() {
        return time;
    }
    
    public int getCharacterSpawn(String coord) {
        if (coord.equals("x") || coord.equals("X")) {
            return characterSpawnX;
        }
        else if (coord.equals("y") || coord.equals("Y")) {
            return characterSpawnY;
        }
        return 0;
    }
    
    private void loadContentsFromFile() {
        InputStream input = getClass().getClassLoader().getResourceAsStream("resources/level" + level + ".txt");
        Scanner scanner;
        if (input != null) {
            scanner = new Scanner(input);
        }
        else {
            scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("resources/gameOver.txt"));
        }
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            int xCoordinate, yCoordinate, xLength, yLength;
            switch (line) {
                case "WALL":
                    xCoordinate = scanner.nextInt();
                    yCoordinate = scanner.nextInt();
                    xLength = scanner.nextInt();
                    yLength = scanner.nextInt();
                    boolean sticky;
                    sticky = scanner.next().equals("true");
                    walls.add(new Wall(xCoordinate, yCoordinate, xLength, yLength, sticky));
                    break;
                case "FLAG":
                    xCoordinate = scanner.nextInt();
                    yCoordinate = scanner.nextInt();
                    flag = new Flag(xCoordinate, yCoordinate);
                    break;
                case "CHARACTER":
                    characterSpawnY = scanner.nextInt();
                    characterSpawnX = scanner.nextInt();
                    break;
                case "TEXT_SCROLLING":
                    scrollingText = scanner.nextLine();
                    hideText = false;
                    break;
                case "TEXT_LARGE":
                    largeText = scanner.nextLine();
                    hideText = false;
                    break;
                case "TEXT_MEDIUM":
                    mediumText = scanner.nextLine();
                    hideText = false;
                    break;
                case "TEXT_SMALL":
                    smallText = scanner.nextLine();
                    hideText = false;
                    break;
            }
        }
    }
    
    private void drawText(Graphics g, Dimension dimensions, String phrase, String type) {
        int heightPercentage = 50;
        Graphics2D g2d = (Graphics2D)g;
        Font font = null;
        switch (type) {
            case "SCROLLING":
                font = new Font("TimesRoman", Font.BOLD, 40);
                heightPercentage = 40;
                break;
            case "LARGE":
                font = new Font("TimesRoman", Font.BOLD, 40);
                heightPercentage = 40;
                break;
            case "MEDIUM":
                font = new Font("TimesRoman", Font.PLAIN, 26);
                heightPercentage = 60;
                break;
            case "SMALL":
                font = new Font("TimesRoman", Font.PLAIN, 18);
                heightPercentage = 30;
                break;
        }
        FontMetrics metrics = g.getFontMetrics(font);
        g2d.setFont(font);
        int x;
        if (type.equals("SCROLLING")) {
            x = (0 - metrics.stringWidth(phrase)) + scrollTick;
            scrollTick = (scrollTick + 2) % (dimensions.width + metrics.stringWidth(phrase));
        }
        else {
            x = (dimensions.width - metrics.stringWidth(phrase)) / 2;
        }
        int y = (((dimensions.height - metrics.getHeight()) / 2) + metrics.getAscent()) * heightPercentage / 50;
        g2d.drawString(phrase, x, y);
    }
    
}
