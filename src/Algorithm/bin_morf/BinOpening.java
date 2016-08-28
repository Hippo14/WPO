package Algorithm.bin_morf;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class BinOpening {

    private BufferedImage templateImage;

    public BinOpening(BufferedImage bufferedImage) {
        BinErosion binErosion = new BinErosion(bufferedImage);
        BinDilation binDilation = new BinDilation(binErosion.getTemplateImage());

        this.templateImage = binDilation.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
