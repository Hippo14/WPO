package Algorithm.image.bmp.read;

import Algorithm.image.bmp.BMPFile;
import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.Pixel;
import Algorithm.image.bmp.bits.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KMacioszek on 2016-08-10.
 */
public class Read {

    BmpHeader header;
    BufferedImage image;

    public Read(InputStream inputStream) throws IOException {
        LitEndInputStream input = new LitEndInputStream(inputStream);

        // File header
        this.header = readHeader(input);

        // Image
        this.image = readImage(input);
    }

    private BmpHeader readHeader(LitEndInputStream input) throws IOException {
        return new BmpHeader(input);
    }

    private BufferedImage readImage(LitEndInputStream input) throws IOException {
        Pixel[] pixels = null;

        if (header.getBitsPerPixel() <= 8)
            pixels = readPixels(header, input);

        if (header.getBitsPerPixel() == 1 && header.getCompressionType() == 0) return Bit1.read1Bit(header, input, pixels);
        else if (header.getBitsPerPixel() == 4 && header.getCompressionType() == 0) return Bit4.read(header, input, pixels);
        else if (header.getBitsPerPixel() == 8 && header.getCompressionType() == 0) return Bit8.read(header, input, pixels);
        else if (header.getBitsPerPixel() == 24 && header.getCompressionType() == 0) return Bit24.read(header, input);
        else if (header.getBitsPerPixel() == 32 && header.getCompressionType() == 0) return Bit32.read(header, input);
        else throw new IOException("Nieznany format bmp: bitCount= " + header.getBitsPerPixel() + ", compression= " + header.getCompressionType());
    }

    private Pixel[] readPixels(BmpHeader bmpHeader, LitEndInputStream litEndInputStream) throws IOException {
        Pixel[] pixels = new Pixel[bmpHeader.getNumColors()];
        for (int i = 0; i < bmpHeader.getNumColors(); i++) {
            Pixel pixel = new Pixel(litEndInputStream);
            pixels[i] = pixel;
        }

        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BmpHeader getHeader() {
        return header;

    }

    public BMPFile getBMP() {
        return new BMPFile(header, image);
    }
}
