package Algorithm.image.bmp.write;

import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.BMPFile;
import Algorithm.image.bmp.bits.*;

import java.awt.image.IndexColorModel;
import java.io.*;

/**
 * Created by MSI on 2016-08-20.
 */
public class Write {

    BMPFile bmp;
    int headerSize;
    int mapBytes;
    int offset;
    int bytesPerLine;
    int fileSize;
    IndexColorModel indexColorModel;

    public void write (BMPFile bmpFile, File file) throws IOException {
        this.bmp = bmpFile;
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

    private BmpHeader createHeader(BMPFile bmpFile) throws IOException {
        return new BmpHeader(bmpFile.getBmpHeader());
    }

    private void write (BMPFile bmpFile, OutputStream outputStream) throws IOException {
        // Create empty header
        BmpHeader header = createHeader(bmpFile);
        // Prepare header
        prepareHeader(header);

        LitEndOutputStream input = new LitEndOutputStream(outputStream);

        // Write header
        writeFileHeader(fileSize, offset, header,input);

        // Write image
        writeImage(header, input);

        // TEST , 'Krzysztof Macioszek' in SHA-512
        byte[] author = "4c701ab9fef083b8ea130642f68be0d0f15d53c9afb80d812a138cdb591943f70b3a4e6b2b619b6741784e7e907feb6df0f8b45e7af04fadbc468f98bc7da867".getBytes("UTF-8");
        input.write(author);
    }

    private void writeImage(BmpHeader header, LitEndOutputStream input) throws IOException {
        if (header.getBitsPerPixel() <= Contants.BITS_8) writeColorMap(indexColorModel, input);

        if (header.getBitsPerPixel() == Contants.BITS_1)       Bit1.write(bmp.getBufferedImage().getRaster(), input);
        else if (header.getBitsPerPixel() == Contants.BITS_4)  Bit4.write(bmp.getBufferedImage().getRaster(), input);
        else if (header.getBitsPerPixel() == Contants.BITS_8)  Bit8.write(bmp.getBufferedImage().getRaster(), input);
        else if (header.getBitsPerPixel() == Contants.BITS_24) Bit24.write(bmp.getBufferedImage().getRaster(), input);
        else if (header.getBitsPerPixel() == Contants.BITS_32) Bit32.write(bmp.getBufferedImage().getRaster(),
                bmp.getBufferedImage().getAlphaRaster(), input);
    }

    private void prepareHeader(BmpHeader header) {
        int mapSize = 0;
        indexColorModel = null;
        if (header.getBitsPerPixel() <= 8) {
            indexColorModel = (IndexColorModel) bmp.getBufferedImage().getColorModel();
            mapSize = indexColorModel.getMapSize();
        }

        // Header size
        headerSize = 14 + header.getSizeOfBitMapInfoHeader();
        // Map Size
        mapBytes = 4 * mapSize;
        // Data offset
        offset = headerSize + mapBytes;
        // Bytes per line
        bytesPerLine = 0;

        if (header.getBitsPerPixel() == Contants.BITS_1) bytesPerLine = Bit1.getBytesPerLine(header.getWidth());
        else if (header.getBitsPerPixel() == Contants.BITS_4)  bytesPerLine = Bit4.getBytesPerLine(header.getWidth());
        else if (header.getBitsPerPixel() == Contants.BITS_8)  bytesPerLine = Bit8.getBytesPerLine(header.getWidth());
        else if (header.getBitsPerPixel() == Contants.BITS_24)  bytesPerLine = Bit24.getBytesPerLine(header.getWidth());
        else if (header.getBitsPerPixel() == Contants.BITS_32)  bytesPerLine = Bit32.getBytesPerLine(header.getWidth());

        // File size
        fileSize = offset + bytesPerLine * header.getHeight();
    }



    private void writeColorMap(IndexColorModel indexColorModel, LitEndOutputStream littleEndianOutputStream) throws IOException {
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

    private void writeFileHeader(int fileSize, int offset, BmpHeader bmpHeader, LitEndOutputStream littleEndianOutputStream) throws IOException {
        byte[] signature = "BM".getBytes("UTF-8");
        littleEndianOutputStream.write(signature);

        littleEndianOutputStream.writeInteger(fileSize);

        // Reserved
        littleEndianOutputStream.writeShort(0);
        littleEndianOutputStream.writeShort(0);

        littleEndianOutputStream.writeInteger(offset);

        bmpHeader.write(littleEndianOutputStream);
    }


}