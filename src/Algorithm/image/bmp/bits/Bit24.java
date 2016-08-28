package Algorithm.image.bmp.bits;

import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.write.LitEndOutputStream;
import Algorithm.image.bmp.read.LitEndInputStream;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

/**
 * Created by MSI on 2016-08-28.
 */
public class Bit24 extends Bit {

    public static BufferedImage read(BmpHeader header, LitEndInputStream input) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(header.getWidth(), header.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster writableRaster = bufferedImage.getRaster();

        int dataPerLine = header.getWidth() * 3;
        int bytesPerLine = dataPerLine;
        if (bytesPerLine % 4 != 0) bytesPerLine = (bytesPerLine / 4 + 1) * 4;
        int padBytesPerLine = bytesPerLine - dataPerLine;

        for (int y = header.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < header.getWidth(); x++) {
                int b = input.readUnsignedByte();
                int g = input.readUnsignedByte();
                int r = input.readUnsignedByte();

                writableRaster.setSample(x, y, 0, r);
                writableRaster.setSample(x, y, 1, g);
                writableRaster.setSample(x, y, 2, b);
            }
            input.skip(padBytesPerLine);
        }

        return bufferedImage;
    }

    public static void write(WritableRaster raster, LitEndOutputStream input) throws IOException {
        int width = raster.getWidth();
        int height = raster.getHeight();

        int bytesPerLine = getBytesPerLine(width);

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int r = raster.getSample(x, y, 0);
                int g = raster.getSample(x, y, 1);
                int b = raster.getSample(x, y, 2);

                input.writeByte(b);
                input.writeByte(g);
                input.writeByte(r);
            }

            for (int i = width * 3; i < bytesPerLine; i++) input.writeByte(0);
        }
    }

    public static int getBytesPerLine(int width) {
        int bytesPerLine = width * 3;
        return (bytesPerLine % 4 != 0) ? (bytesPerLine / 4 + 1) * 4 : bytesPerLine;
    }

}
