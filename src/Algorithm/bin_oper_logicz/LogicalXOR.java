package Algorithm.bin_oper_logicz;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class LogicalXOR extends Logical {

    public LogicalXOR(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage, secondImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        if (x < secondImage.getWidth() && y < secondImage.getHeight()) {
            int p = firstRaster.getSample(x, y, 0);
            int q = secondRaster.getSample(x, y, 0);

            int sum = (p & ~q) | (~p & q);

            if (sum == 0)
                templateImage.setRGB(x, y, 0xffffffff);
            else
                templateImage.setRGB(x, y, 0xff000000);
        }
        else {
            if (firstRaster.getSample(x, y, 0) == 0)
                templateImage.setRGB(x, y, 0xffffffff);
            else
                templateImage.setRGB(x, y, 0xff000000);

        }
    }

}
