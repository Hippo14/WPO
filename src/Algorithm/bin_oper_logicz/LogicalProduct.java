package Algorithm.bin_oper_logicz;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class LogicalProduct extends Logical {

    public LogicalProduct(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage, secondImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        if (x < secondImage.getWidth() && y < secondImage.getHeight()) {
            int sum = firstRaster.getSample(x, y, 0) & secondRaster.getSample(x, y, 0);

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
