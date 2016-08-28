package Algorithm.color_oper_arytm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class ColorImageMultiplication extends Operations {

    public ColorImageMultiplication(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage, secondImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = firstRaster.getSample(x, y, 0);
        int firstGreenPixel = firstRaster.getSample(x, y, 1);
        int firstBluePixel = firstRaster.getSample(x, y, 2);

        if (x < secondImage.getWidth() && y < secondImage.getHeight()) {
            int secondRedPixel = secondRaster.getSample(x, y, 0);
            int secondGreenPixel = secondRaster.getSample(x, y, 1);
            int secondBluePixel = secondRaster.getSample(x, y, 2);

            int redSum = firstRedPixel * secondRedPixel;
            int greenSum = firstGreenPixel * secondGreenPixel;
            int blueSum = firstBluePixel * secondBluePixel;

            if (redSum > 255)
                redSum %= 255;
            if (greenSum > 255)
                greenSum %= 255;
            if (blueSum > 255)
                blueSum %= 255;

            Color color = new Color(redSum, greenSum, blueSum);

            templateImage.setRGB(x, y, color.getRGB());
        }
        else {
            Color color = new Color(firstRedPixel, firstGreenPixel, firstBluePixel);

            templateImage.setRGB(x, y, color.getRGB());
        }
    }
}
