package Algorithm.gray_oper_arytm;

import Algorithm.Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Main abstract class for gray image arithmetics operations.
 *
 * @author Krzysztof Macioszek
 */

public abstract class Operations extends Algorithm {

    BufferedImage firstImage, secondImage, templateImage;
    Raster firstRaster, secondRaster;
    int constant;

    public Operations(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage);

        this.firstImage = setGrayImage(firstImage);
        this.secondImage = setGrayImage(secondImage);

        this.firstRaster = this.firstImage.getRaster();
        this.secondRaster = this.secondImage.getRaster();

        this.templateImage = new BufferedImage(getGrayImage().getWidth(), getGrayImage().getHeight(), getGrayImage().getType());

        if (firstImage != null && secondImage != null)
            run();
    }

    public Operations (BufferedImage firstImage, int constant) {
        super(firstImage);

        this.constant = constant;

        this.firstImage = setGrayImage(firstImage);
        this.firstRaster = this.firstImage.getRaster();

        this.templateImage = new BufferedImage(getGrayImage().getWidth(), getGrayImage().getHeight(), getGrayImage().getType());

        if (firstImage != null)
            run();
    }

    public Operations(BufferedImage firstImage) {
        super(firstImage);

        this.firstImage = setGrayImage(firstImage);
        this.firstRaster = this.firstImage.getRaster();

        this.templateImage = new BufferedImage(getGrayImage().getWidth(), getGrayImage().getHeight(), getGrayImage().getType());

        if (firstImage != null)
            run();
    }

    @Override
    protected void run() {
        for (int x = 0; x < grayImage.getWidth(); x++) {
            for (int y = 0; y < grayImage.getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }
    }

    public abstract void makeAlgorithm(int x, int y);

    protected int getGrayPixel(BufferedImage bufferedImage, int x, int y) {
        int rgb = bufferedImage.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb & 0xFF);

        int grayLevel = (r + g +b) / 3;

        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;

        return gray;

//        Color c = new Color(bufferedImage.getRGB(x, y));
//        int red = (int)(c.getRed() * 0.299);
//        int green = (int)(c.getGreen() * 0.587);
//        int blue = (int)(c.getBlue() * 0.114);
//
//        Color newColor = new Color(red+green+blue, red+green+blue, red+green+blue);
//
//        return newColor.getRGB();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }

//    public BufferedImage setGrayImage(BufferedImage bufferedImage) {
//        BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
//        Graphics graphics = image.getGraphics();
//        graphics.drawImage(bufferedImage, 0, 0, null);
//        graphics.dispose();
//
//        return image;
//    }
}
