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
public class Bit32 extends Bit {

    public static BufferedImage read(BmpHeader header, LitEndInputStream input) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(header.getWidth(), header.getHeight(), BufferedImage.TYPE_INT_ARGB);
        WritableRaster writableRasterRGB = bufferedImage.getRaster();
        WritableRaster writableRasterAlpha = bufferedImage.getAlphaRaster();

        for (int y = header.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < header.getWidth(); x++) {
                int b = input.readUnsignedByte();
                int g = input.readUnsignedByte();
                int r = input.readUnsignedByte();
                int a = input.readUnsignedByte();

                writableRasterRGB.setSample(x, y, 0, r);
                writableRasterRGB.setSample(x, y, 1, g);
                writableRasterRGB.setSample(x, y, 2, b);
                writableRasterAlpha.setSample(x, y, 0, a);
            }
        }

        return bufferedImage;
    }

    public static void write(WritableRaster raster, WritableRaster alphaRaster, LitEndOutputStream input) throws IOException {
        int width = raster.getWidth();
        int height = raster.getHeight();

        int bytesPerLine = getBytesPerLine(width);

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int r = raster.getSample(x, y, 0);
                int g = raster.getSample(x, y, 1);
                int b = raster.getSample(x, y, 2);
                int a = alphaRaster.getSample(x, y, 0);

                input.writeByte(b);
                input.writeByte(g);
                input.writeByte(r);
                input.writeByte(a);
            }
        }
    }

    public static int getBytesPerLine(int width) {
        return width * 4;
    }

}
