package Algorithm.colour_histogram;

import Algorithm.Algorithm;
import Algorithm.gray_hist.GrayHistogram;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * @author Krzysztof Macioszek
 */
public class ThresholdHistogram extends Algorithm {

    BufferedImage templateImage;
    Raster templateRaster;
    int t;

    public ThresholdHistogram(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateImage = new BufferedImage(getFilterImage().getWidth(), getFilterImage().getHeight(), getFilterImage().getType());
        this.templateRaster = binaryImage.getData();
    }

    @Override
    protected void run() {
        int newPixel;
        int bin;

        for (int x = 0; x < binaryImage.getWidth(); x++) {
            for (int y = 0; y < binaryImage.getHeight(); y++) {
                bin = new Color(bufferedImage.getRGB(x, y)).getRed();
                int alpha = new Color(bufferedImage.getRGB(x, y)).getAlpha();

                if (bin > t)
                    newPixel = 255;
                else
                    newPixel = 0;

                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                templateImage.setRGB(x, y, newPixel);
            }
        }
    }

    private int otsuThreshold(BufferedImage bufferedImage) {
        GrayHistogram grayHistogram = new GrayHistogram(bufferedImage);
        grayHistogram.histogram(bufferedImage);
        int[] histogram = grayHistogram.getHistogram();
        int total = bufferedImage.getWidth() * bufferedImage.getHeight();

        float sum = 0;
        for (int i = 0; i < 256; i++)
            sum += i * histogram[i];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0)
                continue;;
            wF = total - wB;

            if (wF == 0)
                break;

            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }

    private BufferedImage binarize(BufferedImage bufferedImage) {
        int bin;
        int newPixel;

        int threshold = otsuThreshold(bufferedImage);

        BufferedImage binarized = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                bin = new Color(bufferedImage.getRGB(x, y)).getRed();
                int alpha = new Color(bufferedImage.getRGB(x, y)).getAlpha();

                if (bin > threshold)
                    newPixel = 255;
                else
                    newPixel = 0;

                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setRGB(x, y, newPixel);
            }
        }

        return binarized;
    }

    private int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }

    public void threshold(int t) {
        this.t = t;
        run();
    }

    public void otsuThreshold() {
        templateImage = binarize(bufferedImage);
    }


    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
