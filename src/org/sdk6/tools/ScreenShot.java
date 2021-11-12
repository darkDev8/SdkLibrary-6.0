package org.sdk6.tools;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Toolkit;

import javax.imageio.ImageIO;

public class ScreenShot implements Runnable {

    private String path;
    private int seconds;

    public ScreenShot(String path, int seconds) {
        this.path = path;
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(seconds * 1000);
            
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
