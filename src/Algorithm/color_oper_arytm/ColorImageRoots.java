package Algorithm.color_oper_arytm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class ColorImageRoots extends Operations {

    public ColorImageRoots(BufferedImage bufferedImage) {
        super(bufferedImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = firstRaster.getSample(x, y, 0);
        int firstGreenPixel = firstRaster.getSample(x, y, 1);
        int firstBluePixel = firstRaster.getSample(x, y, 2);

        firstRedPixel = (int) Math.sqrt((double) firstRedPixel);
        firstGreenPixel = (int) Math.sqrt((double) firstGreenPixel);
        firstBluePixel = (int) Math.sqrt((double) firstBluePixel);

        Color color = new Color(firstRedPixel, firstGreenPixel, firstBluePixel);

        templateImage.setRGB(x, y, color.getRGB());
    }

}