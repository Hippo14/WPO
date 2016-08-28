package Algorithm.gray_morf;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;

/**
 * Main abstract class for gray morphology algorithms.
 *
 * @author Krzysztof Macioszek
 */

public abstract class GrayMorphology extends Algorithm {
    private BufferedImage templateImage;
    private int size = 2;

    public GrayMorphology(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateImage = new BufferedImage(getGrayImage().getWidth(), getGrayImage().getHeight(), getGrayImage().getType());
    }

    @Override
    protected void run() {
        for (int x = 0; x < grayImage.getHeight(); x++) {
            for (int y = 0; y < grayImage.getWidth(); y++) {
                int centralPixel = getGrayPixel(x, y);

                for (int i = x - size; i < x + size; i++) {
                    for (int j = y - size; j < y + size; j++) {
                        if ((i >= 0&&j >= 0) && (i < grayImage.getHeight()&&j < grayImage.getWidth())) {
                            try {
                                centralPixel = makeAlgorithm(centralPixel, i, j);
                            } catch (Exception e) {

                            }
                        }
                    }
                }
                try {
                    templateImage.setRGB(x, y, centralPixel);
                } catch (Exception e) {

                }
            }
        }
    }

    protected int getGrayPixel(int x, int y) {
        int rgb = grayImage.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb & 0xFF);

        int grayLevel = (r + g +b) / 3;
        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
        return gray;
    }

    protected abstract int makeAlgorithm(int centralPixel, int i, int j);

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}


