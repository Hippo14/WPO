import Algorithm.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Krzysztof Macioszek
 */
public class SaveImage {

    File path;

    public SaveImage(File path) {
        this.path = path;
    }

    public void save(BufferedImage bufferedImage) {
        saveImage(bufferedImage);
    }

    private void saveImage(BufferedImage bufferedImage) {
        try {
            ImageIO.write(bufferedImage, "PNG", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
