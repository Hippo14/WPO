package Algorithm.oper_geometr;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class AspectRatioScale extends Algorithm {

    int inputWidth, inputHeight;
    int newWidth, newHeight;

    public AspectRatioScale(BufferedImage bufferedImage) {
        super(bufferedImage);
    }

    public void calculate(int inputWidth, int inputHeight) {
        this.inputWidth = inputWidth;
        this.inputHeight = inputHeight;
        run();
    }

    @Override
    protected void run() {
        int originalWidth, originalHeight;

        originalWidth = bufferedImage.getWidth();
        originalHeight = bufferedImage.getHeight();

        newWidth = inputWidth;
        newHeight = (newWidth * originalHeight) / originalWidth;
    }

    public void setInputHeight(int inputHeight) {
        this.inputHeight = inputHeight;
    }

    public void setInputWidth(int inputWidth) {
        this.inputWidth = inputWidth;
    }

    public int getNewHeight() {
        return newHeight;
    }

    public int getNewWidth() {
        return newWidth;
    }

}
