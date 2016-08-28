package Algorithm.bin_morf;

import Algorithm.*;

import javax.xml.crypto.Data;
import java.awt.image.*;

/**
 * Main abstract class for binary morphology algorithms.
 *
 * @author Krzysztof Macioszek
 */

public abstract class BinaryMorphology extends Algorithm {

    private BufferedImage templateImage;
    private Raster templateRaster;
    private int size = 2;

    /**
     * Class constructor with input image
     *
     * @param bufferedImage input image
     */

    public BinaryMorphology(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateRaster = binaryImage.getData();
        this.templateImage = new BufferedImage(getBinaryImage().getWidth(), getBinaryImage().getHeight(), getBinaryImage().getType());
    }

    /**
     * Method from Algorithms class
     */

    @Override
    protected void run() {
        for (int x = 0; x < binaryImage.getHeight(); x++) {
            for (int y = 0; y < binaryImage.getWidth(); y++) {
                MaskArray mask = new MaskArray(2 * size + 1);
                Integer[][] maskArray = mask.getMaskArray();
                int sum = getBinaryPixel(x, y);
                int k, l;

                for (int i = x - size; i < x + size; i++) {
                    k = 0; l = 0;
                    for (int j = y - size; j < y + size; j++) {
                        if ((i >= 0&&j >= 0) && (i < binaryImage.getHeight()&&j < binaryImage.getWidth())) {
                            try {
                                sum = makeAlgorithm(i, j, k, l, sum, maskArray);
                            } catch (Exception e) {

                            }

//                            sum = sum & getBinaryPixel(i, j) & maskArray[k][l];
                            l++;
                        }
                    }
                    k++;
                }

                if (sum == 1)
                    templateImage.setRGB(x, y, 0xffffffff);
                else
                    templateImage.setRGB(x, y, 0xff000000);
            }
        }
    }

    protected int getBinaryPixel(int x, int y) {
        return this.templateRaster.getSample(x, y, 0);
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }

    public abstract int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray);
}