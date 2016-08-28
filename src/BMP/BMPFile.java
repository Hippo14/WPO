package BMP;

import Algorithm.BmpNew.BmpHeader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by KMacioszek on 2016-06-27.
 */
public class BMPFile {

    BmpHeader bmpHeader;
    BufferedImage bufferedImage;

    public BMPFile (BmpHeader bmpHeader, BufferedImage bufferedImage) {
        this.bmpHeader = bmpHeader;
        this.bufferedImage = bufferedImage;
    }

    public BmpHeader getBmpHeader() {
        return bmpHeader;
    }

    public void setBmpHeader(BmpHeader bmpHeader) {
        this.bmpHeader = bmpHeader;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
