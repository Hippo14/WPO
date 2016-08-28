package Algorithm.color_oper_arytm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class ColorImageDivideConst extends Operations {

    public ColorImageDivideConst(BufferedImage bufferedImage, int i) {
        super(bufferedImage, i);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = firstRaster.getSample(x, y, 0);
        int firstGreenPixel = firstRaster.getSample(x, y, 1);
        int firstBluePixel = firstRaster.getSample(x, y, 2);

        if (constant != 0) {
            firstRedPixel /= constant;
            firstGreenPixel /= constant;
            firstBluePixel /= constant;
        }


        Color color = new Color(firstRedPixel, firstGreenPixel, firstBluePixel);

        templateImage.setRGB(x, y, color.getRGB());
    }
}
