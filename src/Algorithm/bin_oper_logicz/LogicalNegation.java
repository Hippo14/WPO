package Algorithm.bin_oper_logicz;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class LogicalNegation extends Logical {

    public LogicalNegation(BufferedImage bufferedImage) {
        super(bufferedImage);

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
