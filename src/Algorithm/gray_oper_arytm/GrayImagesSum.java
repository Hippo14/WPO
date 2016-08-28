package Algorithm.gray_oper_arytm;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class GrayImagesSum extends Operations {

    public GrayImagesSum(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage, secondImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = getGrayPixel(firstImage, x, y);

        if (x < secondImage.getWidth() && y < secondImage.getHeight()) {
            int secondPixel = getGrayPixel(secondImage, x, y);

            int sum = firstPixel + secondPixel;

            templateImage.setRGB(x, y, sum);
        }
        else {
            templateImage.setRGB(x, y, firstPixel);
        }
    }

}
