package Algorithm.BmpNew;

import Algorithm.BmpNew.decode.LitEndInputStream;
import BMP.BMPFile;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-08-10.
 */
public class BmpHeader {

    int size;
    short reserved1;
    short reserved2;
    int offset;
    int sizeOfBitMapInfoHeader;
    int width;
    int height;
    short planes;
    short bitsPerPixel;
    int compressionType;
    int sizeOfImageDataInBytes;
    int xppm;
    int yppm;
    int numerOfColorsInImage;
    int numberOfImportantColors;

    public BmpHeader() {

    }

    public BmpHeader(BmpHeader header) {
        this.size = header.getSize();
        this.reserved1 = header.getReserved1();
        this.reserved2 = header.getReserved2();
        this.offset = header.getOffset();
        this.sizeOfBitMapInfoHeader = header.getSizeOfBitMapInfoHeader();
        this.width = header.getWidth();
        this.height = header.getHeight();
        this.planes = header.getPlanes();
        this.bitsPerPixel = header.getBitsPerPixel();
        this.compressionType = header.getCompressionType();
        this.sizeOfImageDataInBytes = header.getSizeOfImageDataInBytes();
        this.xppm = header.getXppm();
        this.yppm = header.getYppm();
        this.numerOfColorsInImage = header.getNumerOfColorsInImage();
        this.numberOfImportantColors = header.getNumberOfImportantColors();
    }

    public int getNumColors() {
        return numColors;
    }

    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    private int numColors;

    public int getColorsImportant() {
        return colorsImportant;
    }

    int colorsImportant;

    public BmpHeader(LitEndInputStream litEndInputStream) throws IOException {
        init(litEndInputStream);
    }

    private void init(LitEndInputStream i) throws IOException {
        size = i.readInt();
        reserved1 = i.readShort();
        reserved2 = i.readShort();
        offset = i.readInt();
        sizeOfBitMapInfoHeader = i.readInt();
        width = i.readInt();
        height = i.readInt();
        planes = i.readShort();
        bitsPerPixel = i.readShort();
        compressionType = i.readInt();
        sizeOfImageDataInBytes = i.readInt();
        xppm = i.readInt();
        yppm = i.readInt();
        numerOfColorsInImage = i.readInt();
        numberOfImportantColors = i.readInt();
        numColors = (int) Math.pow(2, bitsPerPixel);
    }

    public void write(LitEndOutputStream litEndOutputStream) throws IOException {
//        litEndOutputStream.writeInteger(size);
//        litEndOutputStream.writeShort(reserved1);
//        litEndOutputStream.writeShort(reserved2);
//        litEndOutputStream.writeInteger(offset);
        litEndOutputStream.writeInteger(sizeOfBitMapInfoHeader);
        litEndOutputStream.writeInteger(width);
        litEndOutputStream.writeInteger(height);
        litEndOutputStream.writeShort(planes);
        litEndOutputStream.writeShort(bitsPerPixel);
        litEndOutputStream.writeInteger(compressionType);
        litEndOutputStream.writeInteger(sizeOfImageDataInBytes);
        litEndOutputStream.writeInteger(xppm);
        litEndOutputStream.writeInteger(yppm);
        litEndOutputStream.writeInteger(numerOfColorsInImage);
        litEndOutputStream.writeInteger(numberOfImportantColors);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public short getReserved1() {
        return reserved1;
    }

    public void setReserved1(short reserved1) {
        this.reserved1 = reserved1;
    }

    public short getReserved2() {
        return reserved2;
    }

    public void setReserved2(short reserved2) {
        this.reserved2 = reserved2;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSizeOfBitMapInfoHeader() {
        return sizeOfBitMapInfoHeader;
    }

    public void setSizeOfBitMapInfoHeader(int sizeOfBitMapInfoHeader) {
        this.sizeOfBitMapInfoHeader = sizeOfBitMapInfoHeader;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public short getPlanes() {
        return planes;
    }

    public void setPlanes(short planes) {
        this.planes = planes;
    }

    public short getBitsPerPixel() {
        return bitsPerPixel;
    }

    public void setBitsPerPixel(short bitsPerPixel) {
        this.bitsPerPixel = bitsPerPixel;
    }

    public int getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(int compressionType) {
        this.compressionType = compressionType;
    }

    public int getSizeOfImageDataInBytes() {
        return sizeOfImageDataInBytes;
    }

    public void setSizeOfImageDataInBytes(int sizeOfImageDataInBytes) {
        this.sizeOfImageDataInBytes = sizeOfImageDataInBytes;
    }

    public int getXppm() {
        return xppm;
    }

    public void setXppm(int xppm) {
        this.xppm = xppm;
    }

    public int getYppm() {
        return yppm;
    }

    public void setYppm(int yppm) {
        this.yppm = yppm;
    }

    public int getNumerOfColorsInImage() {
        return numerOfColorsInImage;
    }

    public void setNumerOfColorsInImage(int numerOfColorsInImage) {
        this.numerOfColorsInImage = numerOfColorsInImage;
    }

    public int getNumberOfImportantColors() {
        return numberOfImportantColors;
    }

    public void setNumberOfImportantColors(int numberOfImportantColors) {
        this.numberOfImportantColors = numberOfImportantColors;
    }

    public void setColorsImportant(int colorsImportant) {
        this.colorsImportant = colorsImportant;
    }


}
