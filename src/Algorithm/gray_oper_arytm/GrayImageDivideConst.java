package Algorithm.gray_oper_arytm;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class GrayImageDivideConst extends Operations {

    public GrayImageDivideConst(BufferedImage firstImage, int constant) {
        super(firstImage, constant);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = getGrayPixel(firstImage, x, y);

        if (constant != 0)
            firstPixel /= constant;

        templateImage.setRGB(x, y, firstPixel);
    }

}
