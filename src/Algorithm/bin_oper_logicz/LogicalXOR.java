package Algorithm.bin_oper_logicz;

import Algorithm.image.bmp.BMPFile;
import Algorithm.utils.exceptions.BadSizeImageException;
import Algorithm.utils.exceptions.BadTypeImageException;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class LogicalXOR extends Logical {

    public LogicalXOR(BMPFile firstImage, BMPFile secondImage) throws BadSizeImageException, BadTypeImageException {
        super(firstImage, secondImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        if (x < secondImage.getBufferedImage().getWidth() && y < secondImage.getBufferedImage().getHeight()) {
            int p = firstRaster.getSample(x, y, 0);
            int q = secondRaster.getSample(x, y, 0);

            int sum = (p & ~q) | (~p & q);

            if (sum == 0)
                firstImage.getBufferedImage().setRGB(x, y, 0xffffffff);
            else
                firstImage.getBufferedImage().setRGB(x, y, 0xff000000);
        }
        else {
            if (firstRaster.getSample(x, y, 0) == 0)
                firstImage.getBufferedImage().setRGB(x, y, 0xffffffff);
            else
                firstImage.getBufferedImage().setRGB(x, y, 0xff000000);

        }
    }

}
