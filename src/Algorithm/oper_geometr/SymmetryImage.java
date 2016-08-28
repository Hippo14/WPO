package Algorithm.oper_geometr;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class SymmetryImage extends Algorithm {

    BufferedImage symmetryImage;

    public SymmetryImage(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.symmetryImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
    }

    @Override
    protected void run() {
    }

    public void symmetryImageOXOY() {
        int width = bufferedImage.getWidth() - 1;


        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            int height = bufferedImage.getHeight() - 1;
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(width, height);
                symmetryImage.setRGB(x, y, rgb);

                height--;
            }
            width--;
        }
    }

    public void symmetryImageOY() {
        int width = bufferedImage.getWidth() - 1;

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(width, y);
                symmetryImage.setRGB(x, y, rgb);
            }
            width--;
        }
    }

    public void symmetryImageOX() {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            int height = bufferedImage.getHeight() - 1;
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, height);
                symmetryImage.setRGB(x, y, rgb);

                height--;
            }
        }
    }

    public BufferedImage getSymmetryImage() {
        return symmetryImage;
    }
}
