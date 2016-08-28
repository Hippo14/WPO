package Algorithm.BmpNew.decode;

import Algorithm.BmpNew.BmpHeader;
import Algorithm.BmpNew.Pixel;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KMacioszek on 2016-08-10.
 */
public class BmpDecode {

    BufferedImage bufferedImage;
    BmpHeader bmpHeader;

    public BmpDecode(InputStream inputStream) throws IOException {
        LitEndInputStream i = new LitEndInputStream(inputStream);

        // HEADER [14]
        byte[] bSignature = new byte[2];
        i.read(bSignature);
        String signature = new String(bSignature, "UTF-8");

        if (!"BM".equals(signature))
            throw new IOException("Niepoprawna sygnatura " + signature);

        // Info header [40]
        bmpHeader = readBmpHeader(i);

        // Color table and raster data
        bufferedImage = read(bmpHeader, i);
    }

    public BmpHeader readBmpHeader(LitEndInputStream litEndInputStream) throws IOException {
        return new BmpHeader(litEndInputStream);
    }

    public BufferedImage read (BmpHeader bmpHeader, LitEndInputStream litEndInputStream) throws IOException {
        Pixel[] pixels = null;

        if (bmpHeader.getBitsPerPixel() <= 8)
            pixels = readPixels(bmpHeader, litEndInputStream);

        return read(bmpHeader, litEndInputStream, pixels);
    }

    public BufferedImage read (BmpHeader bmpHeader, LitEndInputStream litEndInputStream, Pixel[] pixels) throws IOException {
        if (bmpHeader.getBitsPerPixel() == 1 && bmpHeader.getCompressionType() == 0) return read1Bit(bmpHeader, litEndInputStream, pixels);
        else if (bmpHeader.getBitsPerPixel() == 4 && bmpHeader.getCompressionType() == 0) return read4Bit(bmpHeader, litEndInputStream, pixels);
        else if (bmpHeader.getBitsPerPixel() == 8 && bmpHeader.getCompressionType() == 0) return read8Bit(bmpHeader, litEndInputStream, pixels);
        else if (bmpHeader.getBitsPerPixel() == 24 && bmpHeader.getCompressionType() == 0) return read24Bit(bmpHeader, litEndInputStream, pixels);
        else if (bmpHeader.getBitsPerPixel() == 32 && bmpHeader.getCompressionType() == 0) return read32Bit(bmpHeader, litEndInputStream, pixels);
        else throw new IOException("Nieznany format bmp: bitCount= " + bmpHeader.getBitsPerPixel() + ", compression= " + bmpHeader.getCompressionType());
    }

    private BufferedImage read32Bit(BmpHeader bmpHeader, LitEndInputStream litEndInputStream, Pixel[] pixels) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(bmpHeader.getWidth(), bmpHeader.getHeight(), BufferedImage.TYPE_INT_ARGB);
        WritableRaster writableRasterRGB = bufferedImage.getRaster();
        WritableRaster writableRasterAlpha = bufferedImage.getAlphaRaster();

        for (int y = bmpHeader.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < bmpHeader.getWidth(); x++) {
                int b = litEndInputStream.readUnsignedByte();
                int g = litEndInputStream.readUnsignedByte();
                int r = litEndInputStream.readUnsignedByte();
                int a = litEndInputStream.readUnsignedByte();

                writableRasterRGB.setSample(x, y, 0, r);
                writableRasterRGB.setSample(x, y, 1, g);
                writableRasterRGB.setSample(x, y, 2, b);
                writableRasterAlpha.setSample(x, y, 0, a);
            }
        }

        return bufferedImage;
    }

    private BufferedImage read24Bit(BmpHeader bmpHeader, LitEndInputStream litEndInputStream, Pixel[] pixels) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(bmpHeader.getWidth(), bmpHeader.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster writableRaster = bufferedImage.getRaster();

        int dataPerLine = bmpHeader.getWidth() * 3;
        int bytesPerLine = dataPerLine;
        if (bytesPerLine % 4 != 0) bytesPerLine = (bytesPerLine / 4 + 1) * 4;
        int padBytesPerLine = bytesPerLine - dataPerLine;

        for (int y = bmpHeader.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < bmpHeader.getWidth(); x++) {
                int b = litEndInputStream.readUnsignedByte();
                int g = litEndInputStream.readUnsignedByte();
                int r = litEndInputStream.readUnsignedByte();

                writableRaster.setSample(x, y, 0, r);
                writableRaster.setSample(x, y, 1, g);
                writableRaster.setSample(x, y, 2, b);
            }
            litEndInputStream.skip(padBytesPerLine);
        }

        return bufferedImage;
    }

    private BufferedImage read8Bit(BmpHeader bmpHeader, LitEndInputStream litEndInputStream, Pixel[] pixels) throws IOException {
        byte[] r = new byte[pixels.length];
        byte[] g = new byte[pixels.length];
        byte[] b = new byte[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            r[i] = (byte) pixels[i].getRed();
            g[i] = (byte) pixels[i].getGreen();
            b[i] = (byte) pixels[i].getBlue();
        }

        IndexColorModel indexColorModel = new IndexColorModel(8, bmpHeader.getNumColors(), r, g, b);

        BufferedImage bufferedImage = new BufferedImage(bmpHeader.getWidth(), bmpHeader.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, indexColorModel);

        WritableRaster writableRaster = bufferedImage.getRaster();

        int dataPerLine = bmpHeader.getWidth();
        int bytesPerLine = dataPerLine;
        if (bytesPerLine % 4 != 0) bytesPerLine = (bytesPerLine / 4 + 1) * 4;
        int padBytesPerLine = bytesPerLine - dataPerLine;

        for (int y = bmpHeader.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < bmpHeader.getWidth(); x++) {
                int iByte = litEndInputStream.readUnsignedByte();
                writableRaster.setSample(x, y, 0, iByte);
            }

            litEndInputStream.skip(padBytesPerLine);
        }

        return bufferedImage;
    }

    private BufferedImage read4Bit(BmpHeader bmpHeader, LitEndInputStream litEndInputStream, Pixel[] pixels) throws IOException {
        byte[] r = new byte[pixels.length];
        byte[] g = new byte[pixels.length];
        byte[] b = new byte[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            r[i] = (byte) pixels[i].getRed();
            g[i] = (byte) pixels[i].getGreen();
            b[i] = (byte) pixels[i].getBlue();
        }

        IndexColorModel indexColorModel = new IndexColorModel(4, bmpHeader.getNumColors(), r, g, b);

        BufferedImage bufferedImage = new BufferedImage(bmpHeader.getWidth(), bmpHeader.getHeight(), BufferedImage.TYPE_BYTE_BINARY, indexColorModel);

        WritableRaster writableRaster = bufferedImage.getRaster();

        int bitsPerLine = bmpHeader.getWidth() * 4;
        if (bitsPerLine % 32 != 0) bitsPerLine = (bitsPerLine / 32 + 1) * 32;
        int bytesPerLine = (bitsPerLine / 8);
        int[] line = new int[bytesPerLine];
        for (int y = bmpHeader.getHeight() - 1; y >= 0; y--) {
            for (int i = 0; i < bytesPerLine; i++) line[i] = litEndInputStream.readUnsignedByte();

            for (int x = 0; x < bmpHeader.getWidth(); x++) {
                int iByte = x / 2;
                int i = x % 2;
                int n = line[iByte];
                int index = getNibble(n, i);
                writableRaster.setSample(x, y, 0, index);
            }
        }

        return bufferedImage;
    }

    private int getNibble(int n, int i) {
        return (n >> (4 * (1 - i))) & 0xF;
    }

    private BufferedImage read1Bit(BmpHeader bmpHeader, LitEndInputStream litEndInputStream, Pixel[] pixels) throws IOException {
        byte[] r = new byte[pixels.length];
        byte[] g = new byte[pixels.length];
        byte[] b = new byte[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            r[i] = (byte) pixels[i].getRed();
            g[i] = (byte) pixels[i].getGreen();
            b[i] = (byte) pixels[i].getBlue();
        }

        IndexColorModel indexColorModel = new IndexColorModel(1, 2, r, g, b);

        BufferedImage bufferedImage = new BufferedImage(bmpHeader.getWidth(), bmpHeader.getHeight(), BufferedImage.TYPE_BYTE_BINARY, indexColorModel);

        WritableRaster writableRaster = bufferedImage.getRaster();

        int dataBitsPerLine = bmpHeader.getWidth();
        int bitsPerLine = dataBitsPerLine;
        if (bitsPerLine % 32 != 0) bitsPerLine = (bitsPerLine / 32 + 1) * 32;

        int bytesPerLine = (bitsPerLine / 8);
        int[] line = new int[bytesPerLine];

        for (int y = bmpHeader.getHeight() - 1; y >= 0; y--) {
            for (int i = 0; i < bytesPerLine; i++) line[i] = litEndInputStream.readUnsignedByte();

            for (int x = 0; x < bmpHeader.getWidth(); x++) {
                int iByte = x / 8;
                int i = x % 8;
                int n = line[iByte];
                int index = getBit(n, i);
                writableRaster.setSample(x, y, 0, index);
            }
        }

        return bufferedImage;
    }

    private int getBit(int n, int i) {
        return (n >> (7 - i)) & 1;
    }

    private Pixel[] readPixels(BmpHeader bmpHeader, LitEndInputStream litEndInputStream) throws IOException {
        Pixel[] pixels = new Pixel[bmpHeader.getNumColors()];
        for (int i = 0; i < bmpHeader.getNumColors(); i++) {
            Pixel pixel = new Pixel(litEndInputStream);
            pixels[i] = pixel;
        }

        return pixels;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public BmpHeader getBmpHeader() {
        return bmpHeader;
    }
}
