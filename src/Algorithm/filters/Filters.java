package Algorithm.filters;

import Algorithm.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.*;

/**
 * Main abstract class for filters image operations.
 *
 * @author - Krzysztof Macioszek
 */

public abstract class Filters extends Algorithm {

    private BufferedImage templateImage;
    protected int maskSize;
    protected FilterList filterList;
    protected Integer[][] arrayMask;

    public Filters(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.filterList = new FilterList();
        this.arrayMask = filterList.lowPass1();
        this.maskSize = (arrayMask.length);
        this.templateImage = new BufferedImage(getFilterImage().getWidth(), getFilterImage().getHeight(), getFilterImage().getType());
    }

    @Override
    protected void run() {

        int sR, sG, sB;
        int wR, wG, wB;
        int k, l;

        for (int x = maskSize; x < filterImage.getHeight() - maskSize; x++) {
            for (int y = maskSize; y < filterImage.getWidth() - maskSize; y++) {
                // Inicjalizacja zmiennych
                sR = 0; sG = 0; sB = 0;

                k = 0;
                // Oblicz sumy ważone dla każdej składowej piksela (Red, Blue, Green)
                for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {
                    l = 0;
                    for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
                        try {
                            sR = sR + (getRedPixel(i, j) * arrayMask[k][l]);
                            sG = sG + (getGreenPixel(i, j) * arrayMask[k][l]);
                            sB = sB + (getBluePixel(i, j) * arrayMask[k][l]);
                        } catch (Exception e) {
//                            e.printStackTrace();
                        }

                        l++;
                    }
                    k++;
                }

                // Dzielimy przez sumę wszystkich wag maski (jeżeli jest różna od 0)
                int weightSum = getWeightMaskSum(arrayMask);
                if (weightSum == 0)
                    weightSum = Math.abs(weightSum);

                if (weightSum != 0) {
                    wR = sR / weightSum;
                    wG = sG / weightSum;
                    wB = sB / weightSum;
                }
                else {
                    wR = sR;
                    wG = sG;
                    wB = sB;
                }

                wR = wR > 255 ? 255 : wR;
                wG = wG > 255 ? 255 : wG;
                wB = wB > 255 ? 255 : wB;

                wR = wR < 0 ? 0 : wR;
                wG = wG < 0 ? 0 : wG;
                wB = wB < 0 ? 0 : wB;

                Color color = new Color(wR, wG, wB);

                try {
//                        System.out.println(x + " " + y);
                    templateImage.setRGB(x, y, color.getRGB());
                } catch (Exception e) {
//                        e.printStackTrace();
                }
            }
        }

//        RescaleOp rescaleOp = new RescaleOp(0.0f, 0, null);
//        if (maskSize == 3)
//            rescaleOp = new RescaleOp(3.0f, 0, null);
//        else if (maskSize == 5)
//            rescaleOp = new RescaleOp(5.0f, 0, null);
//        else if (maskSize == 7)
//            rescaleOp = new RescaleOp(8.0f, 0, null);
//
//        rescaleOp.filter(templateImage, templateImage);
    }

    protected int getRedPixel(int x, int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb >> 16) & 0xFF;
    }

    protected int getGreenPixel(int x, int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb >> 8) & 0xFF;
    }

    protected int getBluePixel(int x , int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb & 0xFF);
    }

    public BufferedImage getFilterImage() {
        return filterImage;
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }

    public int getWeightMaskSum(Integer[][] maskArray) {
        int sum = 0;
        for (Integer[] list : maskArray) {
            for (Integer number : list) {
                sum += number;
            }
        }
        return sum;
    }

    public void setArrayMask(Integer[][] arrayMask) {
        this.arrayMask = arrayMask;
        this.maskSize = arrayMask.length;
    }
}
