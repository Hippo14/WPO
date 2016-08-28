package Algorithm.color_oper_arytm;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Main abstract class for RGB image arithmetics operations.
 *
 * @author Krzysztof Macioszek
 */

public abstract class Operations extends Algorithm {

    BufferedImage firstImage, secondImage, templateImage;
    Raster firstRaster, secondRaster;
    int constant;

    public Operations(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage);

        this.firstImage = setFilterImage(firstImage);
        this.secondImage = setFilterImage(secondImage);

        this.firstRaster = this.firstImage.getRaster();
        this.secondRaster = this.secondImage.getRaster();

        this.templateImage = new BufferedImage(getFilterImage().getWidth(), getFilterImage().getHeight(), getFilterImage().getType());

        if (firstImage != null && secondImage != null)
            run();
    }

    public Operations (BufferedImage firstImage, int constant) {
        super(firstImage);

        this.firstImage = setFilterImage(firstImage);
        this.firstRaster = this.firstImage.getRaster();

        this.constant = constant;

        this.templateImage = new BufferedImage(getFilterImage().getWidth(), getFilterImage().getHeight(), getFilterImage().getType());

        if (firstImage != null)
            run();
    }

    public Operations(BufferedImage firstImage) {
        super(firstImage);

        this.firstImage = setFilterImage(firstImage);
        this.firstRaster = this.firstImage.getRaster();

        this.templateImage = new BufferedImage(getGrayImage().getWidth(), getGrayImage().getHeight(), getGrayImage().getType());

        if (firstImage != null)
            run();
    }

    public abstract void makeAlgorithm(int x, int y);

    @Override
    protected void run() {
        for (int x = 0; x < firstImage.getWidth(); x++) {
            for (int y = 0; y < firstImage.getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
