package Algorithm.utils;

import Algorithm.image.bmp.BMPFile;
import Algorithm.image.bmp.bits.Contants;
import Algorithm.utils.exceptions.BadTypeImageException;

/**
 * Created by MSI on 2016-08-28.
 */
public class RequiredImage {

    public static boolean checkImage(BMPFile bmpFile, int contants) throws BadTypeImageException {
        if (bmpFile.getBmpHeader().getBinary() == true && contants == Contants.BITS_1) return true;
        else if (bmpFile.getBmpHeader().getGrayscale() == true && (contants == Contants.BITS_4 || contants == Contants.BITS_8)) return true;
        else if (bmpFile.getBmpHeader().getRGB() == true) return true;
        else {
            String typObrazka =  bmpFile.getBmpHeader().getBinary() ? "binarny" : bmpFile.getBmpHeader().getGrayscale() ? "skala szarosci" : "RGB";
            throw new BadTypeImageException("Zly typ obrazka BMP! " + typObrazka);
        }
    }

}
