package Algorithm.bin_oper_logicz;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Main abstract class for image logic operations.
 *
 * @author Krzysztof Macioszek
 */

public abstract class Logical extends Algorithm {

    BufferedImage firstImage, secondImage, templateImage;
    Raster firstRaster, secondRaster;

    public Logical(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage);

        this.firstImage = setBinaryImage(firstImage);
        this.secondImage = setBinaryImage(secondImage);
        this.firstRaster = this.firstImage.getRaster();
        this.secondRaster = this.secondImage.getRaster();

        this.templateImage = new BufferedImage(binaryImage.getWidth(), binaryImage.getHeight(), binaryImage.getType());

        if (firstImage != null && secondImage != null)
            run();
    }

    public Logical(BufferedImage firstImage) {
        super(firstImage);

        this.firstImage = setBinaryImage(firstImage);
        this.firstRaster = this.firstImage.getRaster();

        this.templateImage = new BufferedImage(binaryImage.getWidth(), binaryImage.getHeight(), binaryImage.getType());


        if (firstImage != null)
            run();
    }

    public abstract void makeAlgorithm(int x, int y);

    @Override
    protected void run() {
        for (int x = 0; x < binaryImage.getWidth(); x++) {
            for (int y = 0; y < binaryImage.getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }

    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
