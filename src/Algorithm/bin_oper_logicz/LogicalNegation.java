package Algorithm.bin_oper_logicz;

import Algorithm.image.bmp.BMPFile;
import Algorithm.utils.exceptions.BadTypeImageException;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class LogicalNegation extends Logical {

    public LogicalNegation(BMPFile firstImage) throws BadTypeImageException {
        super(firstImage);

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int bit = firstRaster.getSample(x, y, 0);
        if (bit == 0)
            templateImage.setRGB(x, y, 0xffffffff);
        else
            templateImage.setRGB(x, y, 0xff000000);
    }


}
