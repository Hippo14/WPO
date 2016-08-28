package BMP;

import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.io.FileInputStream;

/**
 * Created by KMacioszek on 2016-06-27.
 */
public class BitmapFactory {

    String dir;

    int bmpFileHeader = 14;
    int bmpInfoHeader = 40;

    int acceptableBits_24 = 24;
    int acceptableBits_8 = 8;

    public BitmapFactory(String dir) {
        this.dir = dir;
    }

    public ImageBMP createImageBMP() {
        ImageBMP image;

        System.out.println("Loading: " + dir);
        try {
            FileInputStream fis = new FileInputStream(dir);

            // Read header file
            byte bf[] = new byte[bmpFileHeader];
            fis.read(bf, 0, bmpFileHeader);

            // Read header info
            byte bi[] = new byte[bmpInfoHeader];
            fis.read(bi, 0, bmpInfoHeader);

            // Interpret data
            int nSize = (((int)bf[5]&0xff)<<24) | (((int)bf[4]&0xff)<<16) | (((int)bf[3]&0xff)<<8) | (int)bf[2]&0xff;

            System.out.println("File type is: " + (char)bf[0] + (char)bf[1]);
            System.out.println("Size of file is: " + nSize);

            int nbiSize = (((int)bi[3]&0xff)<<24) | (((int)bi[2]&0xff)<<16) | (((int)bi[1]&0xff)<<8) | (int)bi[0]&0xff;
            System.out.println("Size of bitmap info header is: " + nbiSize);

            int nWidth = (((int)bi[7]&0xff)<<24) | (((int)bi[6]&0xff)<<16) | (((int)bi[5]&0xff)<<8) | (int)bi[4]&0xff;
            System.out.println("Width is: " + nWidth);
            int nHeight = (((int)bi[11]&0xff)<<24) | (((int)bi[10]&0xff)<<16) | (((int)bi[9]&0xff)<<8) | (int)bi[8]&0xff;
            System.out.println("Height is: " + nHeight);

            int nPlanes = (((int)bi[13]&0xff)<<8) | (int)bi[12]&0xff;
            System.out.println("Planes is: " + nPlanes);

            int nBitCount = (((int)bi[15]&0xff)<<8) | (int)bi[14]&0xff;
            System.out.println("BitCount is: " + nBitCount);

            // Look for non-zero values to indicate compression
            int nCompression = (((int)bi[19])<<24) | (((int)bi[18])<<16) | (((int)bi[17])<<8) | (int)bi[16];
            System.out.println("Compression is : " + nCompression);

            int nSizeImage = (((int)bi[23]&0xff)<<24) | (((int)bi[22]&0xff)<<16) | (((int)bi[21]&0xff)<<8) | (int)bi[20]&0xff;
            System.out.println("SizeImage is: " + nSizeImage);

            int nXPM = (((int)bi[27]&0xff)<<24) | (((int)bi[26]&0xff)<<16) | (((int)bi[25]&0xff)<<8) | (int)bi[24]&0xff;
            System.out.println("X-Pixels per meter is: " + nXPM);
            int nYPM = (((int)bi[31]&0xff)<<24) | (((int)bi[30]&0xff)<<16) | (((int)bi[29]&0xff)<<8) | (int)bi[28]&0xff;
            System.out.println("Y-Pixels per meter is: " + nYPM);

            int nColorsUsed = (((int)bi[35]&0xff)<<24) | (((int)bi[34]&0xff)<<16) | (((int)bi[33]&0xff)<<8) | (int)bi[32]&0xff;
            System.out.println("Colors used are: " + nColorsUsed);
            int nColorsImp = (((int)bi[39]&0xff)<<24) | (((int)bi[38]&0xff)<<16) | (((int)bi[37]&0xff)<<8) | (int)bi[36]&0xff;
            System.out.println("Colors important are: " + nColorsImp);

            if (nBitCount == acceptableBits_24) {
                int nPad = (nSizeImage / nHeight) - nWidth * 3;
                int nData[] = new int[nHeight * nWidth];
                byte brgb[] = new byte[(nWidth + nPad) * 3 * nHeight];
                fis.read(brgb, 0, (nWidth + nPad) * 3 * nHeight);
                int nIndex = 0;

                for (int j = 0; j < nHeight; j++) {
                    for (int i = 0; i < nWidth; i++) {
                        nData [nWidth * (nHeight - j - 1) + i] = (255&0xff0)<<24 | (((int)brgb[nIndex+2]&0xff)<<16) | (((int)brgb[nIndex+1]&0xff)<<8) | (int)brgb[nIndex]&0xff;
                        nIndex += 3;
                    }
                    nIndex += nPad;
                }
                image = createImage(bf, nSize, nbiSize, nWidth, nHeight, nPlanes, nBitCount, nCompression, nSizeImage, nXPM, nYPM, nColorsUsed, nColorsImp, nData);
            }
            else if (nBitCount == acceptableBits_8) {
                int nNumColors = 0;
                if (nColorsUsed > 0)
                    nNumColors = nColorsUsed;
                else
                    nNumColors = (1&0xff)<<nBitCount;
                System.out.println("The number of Colors is: " + nNumColors);

                if (nSizeImage == 0) {
                    nSizeImage = ((((nWidth * nBitCount) + 31) & ~31) >> 3);
                    nSizeImage *= nHeight;
                    System.out.println("nSizeImage (backup) is: " + nSizeImage);
                }

                // Read the palette colors
                int nPalette[] = new int[nNumColors];
                byte bPalette[] = new byte[nNumColors * 4];
                fis.read(bPalette, 0, nNumColors * 4);
                int nIndex8 = 0;

                for (int n = 0; n < nNumColors; n++) {
                    nPalette[n] = (255&0xff)<<24 | (((int)bPalette[nIndex8+2]&0xff)<<16) | (((int)bPalette[nIndex8+1]&0xff)<<8) | (int)bPalette[nIndex8]&0xff;
                    System.out.println ("Palette Color: " + n + " is: "+ nPalette[n]+" (res,R,G,B)= (" +((int)(bPalette[nIndex8+3]) & 0xff)+"," +((int)(bPalette[nIndex8+2]) & 0xff)+"," +((int)bPalette[nIndex8+1]&0xff)+"," +((int)bPalette[nIndex8]&0xff)+")");
                    nIndex8 += 4;
                }

                // Read the image data (actually indices into the palette)
                int nPad8 = (nSizeImage / nHeight) - nWidth;
                System.out.println("nPad is: " + nPad8);
                int nData8[] = new int[nWidth * nHeight];
                byte bData[] = new byte[(nWidth + nPad8) * nHeight];
                fis.read(bData, 0, (nWidth + nPad8) * nHeight);
                nIndex8 = 0;
                for (int j8 = 0; j8 < nHeight; j8++) {
                    for (int i8 = 0; i8 < nWidth; i8++) {
                        nData8[nWidth * (nHeight - j8 - 1) + i8] = nPalette[((int) bData[nIndex8]&0xff)];
                        nIndex8++;
                    }
                    nIndex8 += nPad8;
                }

                image = createImage(bf, nSize, nbiSize, nWidth, nHeight, nPlanes, nBitCount, nCompression, nSizeImage, nXPM, nYPM, nColorsUsed, nColorsImp, nData8);
//                image = createImage(new MemoryImageSource(nWidth, nHeight, nData8, 0 , nWidth));
            }
            else {
                System.out.println("Not a 24-bit or 8-bit Windows Bitmap, aborting...");
                image = (ImageBMP) null;
            }
            fis.close();
            return image;

        } catch (Exception e) {
            System.out.println("Caught exception in loadbitmap!");
            e.printStackTrace();

        }

        return (ImageBMP) null;
    }

    private ImageBMP createImage(byte[] bf, int nSize, int nbiSize, int nWidth, int nHeight, int nPlanes, int nBitCount, int nCompression, int nSizeImage, int nXPM, int nYPM, int nColorsUsed, int nColorsImp, int[] nData) {
        return new ImageBMP(bf, nSize, nbiSize, nWidth, nHeight, nPlanes, nBitCount, nCompression, nSizeImage, nXPM, nYPM, nColorsUsed, nColorsImp, nData);
    }

    private Image createImage(MemoryImageSource memoryImageSource) {
        return Toolkit.getDefaultToolkit().createImage(memoryImageSource);
    }

}
