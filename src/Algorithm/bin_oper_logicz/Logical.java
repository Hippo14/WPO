package Algorithm.bin_oper_logicz;

import Algorithm.Algorithm;
import Algorithm.image.bmp.BMPFile;
import Algorithm.image.bmp.bits.Contants;
import Algorithm.utils.RequiredImage;
import Algorithm.utils.exceptions.BadSizeImageException;
import Algorithm.utils.exceptions.BadTypeImageException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Main abstract class for image logic operations.
 *
 * @author Krzysztof Macioszek
 */

public abstract class Logical extends Algorithm {

    BMPFile firstImage;
    BMPFile secondImage;

    Raster firstRaster, secondRaster;

    BufferedImage templateImage;

    public Logical(BMPFile firstImage, BMPFile secondImage) throws BadSizeImageException, BadTypeImageException {
        this.firstImage = firstImage;
        this.secondImage = secondImage;
        this.firstRaster = this.firstImage.getBufferedImage().getRaster();
        this.secondRaster = this.secondImage.getBufferedImage().getRaster();

        if (firstImage.getBmpHeader().getWidth() == secondImage.getBmpHeader().getWidth() && firstImage.getBmpHeader().getHeight() == secondImage.getBmpHeader().getHeight()) {
            if (RequiredImage.checkImage(firstImage, Contants.BITS_1) || RequiredImage.checkImage(secondImage, Contants.BITS_1))
                if (firstImage != null && secondImage != null)
                    run();
        }
        else throw new BadSizeImageException("Obrazki nie maja tych samych rozmiarow!");

    }

    public Logical(BMPFile firstImage) throws BadTypeImageException {
        this.firstImage = firstImage;
        this.firstRaster = this.firstImage.getBufferedImage().getRaster();
        this.templateImage = setBinaryImage(this.firstImage.getBufferedImage());

        if (RequiredImage.checkImage(firstImage, Contants.BITS_1))
            if (firstImage != null)
                run();
    }

    public abstract void makeAlgorithm(int x, int y);

    protected void run() {
        for (int x = 0; x < firstImage.getBmpHeader().getWidth(); x++) {
            for (int y = 0; y < firstImage.getBmpHeader().getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }

    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
