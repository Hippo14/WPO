package BMP;

import javafx.scene.image.*;

import java.awt.*;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.io.FileInputStream;

/**
 * Created by KMacioszek on 2016-06-13.
 */
public class ImageBMP {

    char[] fileType;
    int fileSize;
    int headerSize;
    int width;
    int height;
    int planes;
    int bitcount;
    int compression;
    int imageSize;
    int xpm;
    int ypm;
    int colorUsed;
    int colorImportant;
    int[] pixels;

    public ImageBMP(byte[] bf, int nSize, int nbiSize, int nWidth, int nHeight, int nPlanes, int nBitCount, int nCompression, int nSizeImage, int nXPM, int nYPM, int nColorsUsed, int nColorsImp, int[] nData) {
        this.fileType = new char[]{(char) bf[0], (char) bf[1]};
        this.fileSize = nSize;
        this.headerSize = nbiSize;
        this.width = nWidth;
        this.height = nHeight;
        this.planes = nPlanes;
        this.bitcount = nBitCount;
        this.compression = nCompression;
        this.imageSize = nSizeImage;
        this.xpm = nXPM;
        this.ypm = nYPM;
        this.colorUsed = nColorsUsed;
        this.colorImportant = nColorsImp;
        this.pixels = nData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
