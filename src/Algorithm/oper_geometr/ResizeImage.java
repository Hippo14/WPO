package Algorithm.oper_geometr;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class ResizeImage {

    int newWidth, newHeight;
    BufferedImage bufferedImage;

    public ResizeImage(BufferedImage bufferedImage, int newWidth, int newHeight) {
        this.bufferedImage = bufferedImage;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    public BufferedImage resize() {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, bufferedImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }

}
