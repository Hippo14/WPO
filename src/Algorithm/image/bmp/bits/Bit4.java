package Algorithm.image.bmp.bits;

import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.Pixel;
import Algorithm.image.bmp.read.LitEndInputStream;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

/**
 * Created by MSI on 2016-08-28.
 */
public class Bit4 extends Bit {

    public static BufferedImage read(BmpHeader header, LitEndInputStream input, Pixel[] pixels) throws IOException {
        byte[] r = new byte[pixels.length];
        byte[] g = new byte[pixels.length];
        byte[] b = new byte[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            r[i] = (byte) pixels[i].getRed();
            g[i] = (byte) pixels[i].getGreen();
            b[i] = (byte) pixels[i].getBlue();
        }

        IndexColorModel indexColorModel = new IndexColorModel(4, header.getNumColors(), r, g, b);

        BufferedImage bufferedImage = new BufferedImage(header.getWidth(), header.getHeight(), BufferedImage.TYPE_BYTE_BINARY, indexColorModel);

        WritableRaster writableRaster = bufferedImage.getRaster();

        int bitsPerLine = header.getWidth() * 4;
        if (bitsPerLine % 32 != 0) bitsPerLine = (bitsPerLine / 32 + 1) * 32;
        int bytesPerLine = (bitsPerLine / 8);
        int[] line = new int[bytesPerLine];
        for (int y = header.getHeight() - 1; y >= 0; y--) {
            for (int i = 0; i < bytesPerLine; i++) line[i] = input.readUnsignedByte();

            for (int x = 0; x < header.getWidth(); x++) {
                int iByte = x / 2;
                int i = x % 2;
                int n = line[iByte];
                int index = getNibble(n, i);
                writableRaster.setSample(x, y, 0, index);
            }
        }

        return bufferedImage;
    }

}
