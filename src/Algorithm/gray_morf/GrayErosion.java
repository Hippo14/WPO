package Algorithm.gray_morf;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class GrayErosion extends GrayMorphology {

    public GrayErosion(BufferedImage bufferedImage) {
        super(bufferedImage);

        run();
    }

    @Override
    protected int makeAlgorithm(int centralPixel, int i, int j) {
        int actualPixel = getGrayPixel(i, j);
        if (actualPixel > centralPixel)
            centralPixel = actualPixel;
        return centralPixel;
    }
}
