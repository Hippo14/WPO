package Algorithm.color_oper_arytm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class ColorImageExponentation extends Operations {

    public ColorImageExponentation(BufferedImage bufferedImage, int i) {
        super(bufferedImage, i);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = firstRaster.getSample(x, y, 0);
        int firstGreenPixel = firstRaster.getSample(x, y, 1);
        int firstBluePixel = firstRaster.getSample(x, y, 2);

        firstRedPixel = (int)Math.pow(firstRedPixel, constant);
        firstGreenPixel = (int)Math.pow(firstGreenPixel, constant);
        firstBluePixel = (int)Math.pow(firstBluePixel, constant);

        if (firstRedPixel > 255)
            firstRedPixel = 255;
        if (firstGreenPixel > 255)
            firstGreenPixel = 255;
        if (firstBluePixel > 255)
            firstBluePixel = 255;

        Color color = new Color(firstRedPixel, firstGreenPixel, firstBluePixel);

        templateImage.setRGB(x, y, color.getRGB());
    }
}
