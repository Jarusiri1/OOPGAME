package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Resource {
    public static BufferedImage getResourceImage(String path) {
        BufferedImage img = null;
        File file = new File(path);
        if (file.exists()) {
            try {
                img = ImageIO.read(file);
            } catch (IOException ex) {
                System.err.println("Error reading the image file: " + path);
                ex.printStackTrace();
            }
        } else {
            System.err.println("File not found: " + path);
        }
        return img;
    }
}
