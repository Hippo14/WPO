package Algorithm.image.bmp.bits;

import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.write.LitEndOutputStream;
import Algorithm.image.bmp.Pixel;
import Algorithm.image.bmp.read.LitEndInputStream;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

/**
 * Created by MSI on 2016-08-28.
 */
public class Bit1 extends Bit {

    public static BufferedImage read1Bit(BmpHeader header, LitEndInputStream input, Pixel[] pixels) throws IOException {
        byte[] r = new byte[pixels.length];
        byte[] g = new byte[pixels.length];
        byte[] b = new byte[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            r[i] = (byte) pixels[i].getRed();
            g[i] = (byte) pixels[i].getGreen();
            b[i] = (byte) pixels[i].getBlue();
        }

        IndexColorModel indexColorModel = new IndexColorModel(1, 2, r, g, b);

        BufferedImage bufferedImage = new BufferedImage(header.getWidth(), header.getHeight(), BufferedImage.TYPE_BYTE_BINARY, indexColorModel);

        WritableRaster writableRaster = bufferedImage.getRaster();

        int dataBitsPerLine = header.getWidth();
        int bitsPerLine = dataBitsPerLine;
        if (bitsPerLine % 32 != 0) bitsPerLine = (bitsPerLine / 32 + 1) * 32;

        int bytesPerLine = (bitsPerLine / 8);
        int[] line = new int[bytesPerLine];

        for (int y = header.getHeight() - 1; y >= 0; y--) {
            for (int i = 0; i < bytesPerLine; i++) line[i] = input.readUnsignedByte();

            for (int x = 0; x < header.getWidth(); x++) {
                int iByte = x / 8;
                int i = x % 8;
                int n = line[iByte];
                int index = getBit(n, i);
                writableRaster.setSample(x, y, 0, index);
            }
        }

        return bufferedImage;
    }

    public static void write(WritableRaster raster, LitEndOutputStream input) throws IOException {
        int bytesPerLine = getBytesPerLine(raster.getWidth());

        byte[] line = new byte[bytesPerLine];

        for (int y = raster.getHeight() - 1; y >=0; y--) {
            for (int i = 0; i < bytesPerLine; i++) line[i] = 0;

            for (int x = 0; x < raster.getWidth(); x++) {
                int bi = x / 8;
                int i = x % 8;
                int index = raster.getSample(x, y, 0);
                line[bi] = setBit(line[bi], i, index);
            }

            input.write(line);
        }
    }

    public static int getBytesPerLine(int width) {
        int bytesPerLine = width / 8;
        if (bytesPerLine * 8 < width) bytesPerLine++;
        if (bytesPerLine % 4 != 0) bytesPerLine = ( bytesPerLine / 4 + 1 ) * 4;
        return bytesPerLine;
    }

}
