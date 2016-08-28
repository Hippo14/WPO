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
public class Bit8 extends Bit {

    public static BufferedImage read(BmpHeader header, LitEndInputStream input, Pixel[] pixels) throws IOException {
        byte[] r = new byte[pixels.length];
        byte[] g = new byte[pixels.length];
        byte[] b = new byte[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            r[i] = (byte) pixels[i].getRed();
            g[i] = (byte) pixels[i].getGreen();
            b[i] = (byte) pixels[i].getBlue();
        }

        IndexColorModel indexColorModel = new IndexColorModel(8, header.getNumColors(), r, g, b);

        BufferedImage bufferedImage = new BufferedImage(header.getWidth(), header.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, indexColorModel);

        WritableRaster writableRaster = bufferedImage.getRaster();

        int dataPerLine = header.getWidth();
        int bytesPerLine = dataPerLine;
        if (bytesPerLine % 4 != 0) bytesPerLine = (bytesPerLine / 4 + 1) * 4;
        int padBytesPerLine = bytesPerLine - dataPerLine;

        for (int y = header.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < header.getWidth(); x++) {
                int iByte = input.readUnsignedByte();
                writableRaster.setSample(x, y, 0, iByte);
            }

            input.skip(padBytesPerLine);
        }

        return bufferedImage;
    }

}
