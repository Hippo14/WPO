package Algorithm.gray_morf;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class GrayClosing {

    private BufferedImage templateImage;

    public GrayClosing(BufferedImage bufferedImage) {
        GrayDilation grayDilation = new GrayDilation(bufferedImage);
        GrayErosion grayErosion = new GrayErosion(grayDilation.getTemplateImage());

        this.templateImage = grayErosion.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
