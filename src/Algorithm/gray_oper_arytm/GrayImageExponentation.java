package Algorithm.gray_oper_arytm;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class GrayImageExponentation extends Operations {

    public GrayImageExponentation(BufferedImage firstImage, int constant) {
        super(firstImage, constant);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = getGrayPixel(firstImage, x, y);

        firstPixel = (int)Math.pow(firstPixel, constant);

        templateImage.setRGB(x, y, firstPixel);
    }
}
