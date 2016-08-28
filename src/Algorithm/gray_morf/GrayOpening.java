package Algorithm.gray_morf;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class GrayOpening {
    private BufferedImage templateImage;

    public GrayOpening(BufferedImage bufferedImage) {
        GrayErosion grayErosion = new GrayErosion(bufferedImage);
        GrayDilation grayDilation = new GrayDilation(grayErosion.getTemplateImage());

        this.templateImage = grayDilation.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
