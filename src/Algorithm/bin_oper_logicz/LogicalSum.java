package Algorithm.bin_oper_logicz;

import Algorithm.Algorithm;
import Algorithm.image.bmp.BMPFile;
import Algorithm.utils.exceptions.BadSizeImageException;
import Algorithm.utils.exceptions.BadTypeImageException;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * @author Krzysztof Macioszek
 */
public class LogicalSum extends Logical {

    public LogicalSum(BMPFile firstImage, BMPFile secondImage) throws BadSizeImageException, BadTypeImageException {
        super(firstImage, secondImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        if (x < templateImage.getWidth() && y < templateImage.getHeight()) {
            int sum = firstRaster.getSample(x, y, 0) | secondRaster.getSample(x, y, 0);

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
