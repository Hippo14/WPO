package Algorithm.filters;

import Algorithm.Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class FilterMaximum extends Algorithm {

    int maskSize = 3;
    BufferedImage templateImage;

    public FilterMaximum(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateImage = new BufferedImage(filterImage.getWidth(), filterImage.getHeight(), filterImage.getType());

        run();
    }

    @Override
    protected void run() {
        int minimumRedPixel, minimumGreenPixel, minimumBluePixel;

        for (int x = maskSize; x < filterImage.getHeight() - maskSize; x++) {
            for (int y = maskSize; y < filterImage.getWidth() - maskSize; y++) {
                minimumRedPixel = getRedPixel(x, y);
                minimumGreenPixel = getGreenPixel(x, y);
                minimumBluePixel = getBluePixel(x, y);

                for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {
                    for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
                        if (minimumRedPixel < getRedPixel(i, j))
                            minimumRedPixel = getRedPixel(i, j);
                        if (minimumGreenPixel < getGreenPixel(i, j))
                            minimumGreenPixel = getGreenPixel(i, j);
                        if (minimumBluePixel < getBluePixel(i, j))
                            minimumBluePixel = getBluePixel(i, j);
                    }
                }

                Color color = new Color(minimumRedPixel, minimumGreenPixel, minimumBluePixel);

                templateImage.setRGB(x, y, color.getRGB());
            }
        }
    }

    private int getRedPixel(int x, int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb >> 16) & 0xFF;
    }

    private int getGreenPixel(int x, int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb >> 8) & 0xFF;
    }

    private int getBluePixel(int x, int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb & 0xFF);
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }

}
