package Algorithm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Krzysztof Macioszek
 */
abstract public class Image {

    protected BufferedImage bufferedImage;
    protected String path;

    public Image(String path) {
        this.bufferedImage = openImage(path);
        this.path = path;
    }

    protected Image() {
    }

    public void setImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void saveImage(BufferedImage bufferedImage, String name) {
        path = new StringBuffer(path).insert(path.length() - 4, name).toString();
        try {
            ImageIO.write(bufferedImage, "PNG", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage openImage(String path) {
        BufferedImage bufferedImage = null;
        try {
             bufferedImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

}