package Algorithm.filters;

import Algorithm.Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Krzysztof Macioszek
 */
public class FilterMedian extends Algorithm {

    int maskSize = 3;
    int medianRed, medianGreen, medianBlue;
    Integer[] redPixels, greenPixels, bluePixels;
    BufferedImage templateImage;

    public FilterMedian(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateImage = new BufferedImage(filterImage.getWidth(), filterImage.getHeight(), filterImage.getType());

        run();
    }

    @Override
    protected void run() {
        int z;

        for (int x = maskSize; x < filterImage.getHeight() - maskSize; x++) {
            for (int y = maskSize; y < filterImage.getWidth() - maskSize; y++) {
                redPixels = new Integer[2 * maskSize + 1];
                greenPixels = new Integer[2 * maskSize + 1];
                bluePixels = new Integer[2 * maskSize + 1];

                z = 0;

                for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {
                    for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
                        try {
                            redPixels[z] = getRedPixel(i, j);
                            greenPixels[z] = getGreenPixel(i, j);
                            bluePixels[z] = getBluePixel(i, j);
                        } catch (Exception e) {

                        }

                        z++;
                    }
                }

                Arrays.sort(redPixels);
                Arrays.sort(greenPixels);
                Arrays.sort(bluePixels);

                medianRed = getMedianFromArray(redPixels);
                medianGreen = getMedianFromArray(greenPixels);
                medianBlue = getMedianFromArray(bluePixels);

                Color color = new Color(medianRed, medianGreen, medianBlue);

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

    private int getMedianFromArray(Integer[] pixelArray) {
        if (pixelArray.length % 2 == 0)
            return (pixelArray[pixelArray.length / 2] + pixelArray[pixelArray.length / 2 - 1]) / 2;
        else
            return pixelArray[pixelArray.length / 2];
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
