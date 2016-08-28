package Algorithm.image.bmp.encode;

import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.LitEndOutputStream;
import Algorithm.image.bmp.BMPFile;

import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * Created by MSI on 2016-08-20.
 */
public class BmpEncode {

    public BmpEncode() throws IOException {
    }

    public static void write (BMPFile bmpFile, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            write(bmpFile, bufferedOutputStream);
            bufferedOutputStream.flush();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {

            }
        }
    }

    private static BmpHeader createHeader(BMPFile bmpFile) throws IOException {
        return new BmpHeader(bmpFile.getBmpHeader());
    }

    private static void write (BMPFile bmpFile, OutputStream outputStream) throws IOException {
        BmpHeader bmpHeader = createHeader(bmpFile);

        int mapSize = 0;
        IndexColorModel indexColorModel = null;
        if (bmpHeader.getBitsPerPixel() <= 8) {
            indexColorModel = (IndexColorModel) bmpFile.getBufferedImage().getColorModel();
            mapSize = indexColorModel.getMapSize();
        }

        // Header size
        int headerSize = 14 + bmpHeader.getSizeOfBitMapInfoHeader();
        // Map Size
        int mapBytes = 4 * mapSize;
        // Data offset
        int offset = headerSize + mapBytes;
        // Bytes per line
        int bytesPerLine = 0;

        if (bmpHeader.getBitsPerPixel() == 1) bytesPerLine = getBytesPerLine1Bit(bmpHeader.getWidth());
        else if (bmpHeader.getBitsPerPixel() == 4)  bytesPerLine = getBytesPerLine4Bit(bmpHeader.getWidth());
        else if (bmpHeader.getBitsPerPixel() == 8)  bytesPerLine = getBytesPerLine8Bit(bmpHeader.getWidth());
        else if (bmpHeader.getBitsPerPixel() == 24)  bytesPerLine = getBytesPerLine24Bit(bmpHeader.getWidth());
        else if (bmpHeader.getBitsPerPixel() == 32)  bytesPerLine = getBytesPerLine32Bit(bmpHeader.getWidth());

        // File size
        int fileSize = offset + bytesPerLine * bmpHeader.getHeight();

        LitEndOutputStream littleEndianOutputStream = new LitEndOutputStream(outputStream);

        writeFileHeader(fileSize, offset, bmpHeader,littleEndianOutputStream);

        if (bmpHeader.getBitsPerPixel() <= 8) writeColorMap(indexColorModel, littleEndianOutputStream);

        if (bmpHeader.getBitsPerPixel() == 1) write1(bmpFile.getBufferedImage().getRaster(), littleEndianOutputStream);
        else if (bmpHeader.getBitsPerPixel() == 4)  write4(bmpFile.getBufferedImage().getRaster(), littleEndianOutputStream);
        else if (bmpHeader.getBitsPerPixel() == 8)  write8(bmpFile.getBufferedImage().getRaster(), littleEndianOutputStream);
        else if (bmpHeader.getBitsPerPixel() == 24)  write24(bmpFile.getBufferedImage().getRaster(), littleEndianOutputStream);
        else if (bmpHeader.getBitsPerPixel() == 32)  write32(bmpFile.getBufferedImage().getRaster(),
                bmpFile.getBufferedImage().getAlphaRaster(), littleEndianOutputStream);

        // TEST
        byte[] author = "Krzysztof Macioszek".getBytes("UTF-8");
        littleEndianOutputStream.write(author);
    }

    private static void write32(WritableRaster raster, WritableRaster alphaRaster, LitEndOutputStream littleEndianOutputStream) throws IOException {
        int width = raster.getWidth();
        int height = raster.getHeight();

        int bytesPerLine = getBytesPerLine24Bit(width);

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int r = raster.getSample(x, y, 0);
                int g = raster.getSample(x, y, 1);
                int b = raster.getSample(x, y, 2);
                int a = alphaRaster.getSample(x, y, 0);

                littleEndianOutputStream.writeByte(b);
                littleEndianOutputStream.writeByte(g);
                littleEndianOutputStream.writeByte(r);
                littleEndianOutputStream.writeByte(a);
            }
        }
    }

    private static void write24(WritableRaster raster, LitEndOutputStream littleEndianOutputStream) throws IOException {
        int width = raster.getWidth();
        int height = raster.getHeight();

        int bytesPerLine = getBytesPerLine24Bit(width);

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int r = raster.getSample(x, y, 0);
                int g = raster.getSample(x, y, 1);
                int b = raster.getSample(x, y, 2);

                littleEndianOutputStream.writeByte(b);
                littleEndianOutputStream.writeByte(g);
                littleEndianOutputStream.writeByte(r);
            }

            for (int i = width * 3; i < bytesPerLine; i++) littleEndianOutputStream.writeByte(0);
        }
    }

    private static void write8(WritableRaster raster, LitEndOutputStream littleEndianOutputStream) throws IOException {
        int width = raster.getWidth();
        int height = raster.getHeight();

        int bytesPerLine = getBytesPerLine8Bit(width);

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int index = raster.getSample(x, y, 0);
                littleEndianOutputStream.writeByte(index);
            }
            for (int i = width; i < bytesPerLine; i++) littleEndianOutputStream.writeByte(0);
        }
    }

    private static void write4(WritableRaster raster, LitEndOutputStream littleEndianOutputStream) throws IOException {
        int width = raster.getWidth();
        int height = raster.getHeight();

        int bytesPerLine = getBytesPerLine4Bit(width);

        byte[] line = new byte[bytesPerLine];

        for (int y = height - 1; y >= 0; y--) {
            for (int i = 0; i < bytesPerLine; i++) line[i] = 0;

            for (int x = 0; x < width; x++) {
                int bi = x / 2;
                int i = x % 2;
                int index = raster.getSample(x, y, 0);

                line[bi] = setNibble(line[bi], i , index);
            }

            littleEndianOutputStream.write(line);
        }
    }

    private static byte setNibble(byte b, int i, int index) {
        return b |= (index << ((1 - i) * 4));
    }

    private static void write1(WritableRaster raster, LitEndOutputStream littleEndianOutputStream) throws IOException {
        int bytesPerLine = getBytesPerLine1Bit(raster.getWidth());

        byte[] line = new byte[bytesPerLine];

        for (int y = raster.getHeight() - 1; y >=0; y--) {
            for (int i = 0; i < bytesPerLine; i++) line[i] = 0;

            for (int x = 0; x < raster.getWidth(); x++) {
                int bi = x / 8;
                int i = x % 8;
                int index = raster.getSample(x, y, 0);
                line[bi] = setBit(line[bi], i, index);
            }

            littleEndianOutputStream.write(line);
        }
    }

    private static byte setBit(byte b, int i, int index) {
        if (index == 0) {
            b &= ~(1 << (7 - i));
        }
        else {
            b |= 1 << (7 - i);
        }
        return b;
    }

    private static void writeColorMap(IndexColorModel indexColorModel, LitEndOutputStream littleEndianOutputStream) throws IOException {
        int mapSize = indexColorModel.getMapSize();
        for (int i = 0; i < mapSize; i++) {
            int rgb = indexColorModel.getRGB(i);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = (rgb) & 0xFF;

            littleEndianOutputStream.writeByte(b);
            littleEndianOutputStream.writeByte(g);
            littleEndianOutputStream.writeByte(r);
            littleEndianOutputStream.writeByte(0);
        }
    }

    private static void writeFileHeader(int fileSize, int offset, BmpHeader bmpHeader, LitEndOutputStream littleEndianOutputStream) throws IOException {
        byte[] signature = "BM".getBytes("UTF-8");
        littleEndianOutputStream.write(signature);

        littleEndianOutputStream.writeInteger(fileSize);

        // Reserved
        littleEndianOutputStream.writeShort(0);
        littleEndianOutputStream.writeShort(0);

        littleEndianOutputStream.writeInteger(offset);

        bmpHeader.write(littleEndianOutputStream);
    }

    private static int getBytesPerLine32Bit(int width) {
        return width * 4;
    }

    private static int getBytesPerLine24Bit(int width) {
        int bytesPerLine = width * 3;
        return (bytesPerLine % 4 != 0) ? (bytesPerLine / 4 + 1) * 4 : bytesPerLine;
    }

    private static int getBytesPerLine8Bit(int width) {
        int bytesPerLine = width;
        return (bytesPerLine % 4 != 0) ? ( bytesPerLine / 4 +1 ) * 4 : bytesPerLine;
    }

    private static int getBytesPerLine4Bit(int width) {
        int bytesPerLine = width / 2;
        return (bytesPerLine % 4 != 0) ? ( bytesPerLine / 4 + 1 ) * 4 : bytesPerLine;
    }

    private static int getBytesPerLine1Bit(int width) {
        int bytesPerLine = width / 8;
        if (bytesPerLine * 8 < width) bytesPerLine++;
        if (bytesPerLine % 4 != 0) bytesPerLine = ( bytesPerLine / 4 + 1 ) * 4;
        return bytesPerLine;
    }

}