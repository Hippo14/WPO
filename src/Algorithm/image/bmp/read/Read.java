package Algorithm.image.bmp.read;

import Algorithm.image.bmp.BMPFile;
import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.Pixel;
import Algorithm.image.bmp.bits.*;
import Algorithm.image.bmp.exceptions.UnknownFormatException;

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

        if (header.getBitsPerPixel() <= 8) pixels = readPixels(header, input);

        // Check pixel colors (binary, grayscale, rgb)
        if (header.getBitsPerPixel() == Contants.BITS_1)
            header.setBinary(true);
        else if (header.getBitsPerPixel() <= 8 && checkGrayscale(pixels))
            header.setGrayscale(true);
        else
            header.setRGB(true);


        if (header.getBitsPerPixel() == Contants.BITS_1 && header.getCompressionType() == 0) return Bit1.read1Bit(header, input, pixels);
        else if (header.getBitsPerPixel() == Contants.BITS_4 && header.getCompressionType() == 0) return Bit4.read(header, input, pixels);
        else if (header.getBitsPerPixel() == Contants.BITS_8 && header.getCompressionType() == 0) return Bit8.read(header, input, pixels);
        else if (header.getBitsPerPixel() == Contants.BITS_24 && header.getCompressionType() == 0) return Bit24.read(header, input);
        else if (header.getBitsPerPixel() == Contants.BITS_32 && header.getCompressionType() == 0) return Bit32.read(header, input);
        else throw new UnknownFormatException("Nieznany format bmp: bitCount= " + header.getBitsPerPixel() + ", compression= " + header.getCompressionType());
    }

    private boolean checkGrayscale(Pixel[] pixels) {
        for (int x = 0; x < pixels.length; x++) {
            if (pixels[x].getRed() != pixels[x].getGreen() && pixels[x].getRed() != pixels[x].getBlue()) {
                return false;
            }
        }
        return true;
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
