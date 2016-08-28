package Algorithm.bin_morf;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class BinClosing {
    private BufferedImage templateImage;

    public BinClosing(BufferedImage bufferedImage) {
        BinDilation binDilation = new BinDilation(bufferedImage);
        BinErosion binErosion = new BinErosion(binDilation.getTemplateImage());

        this.templateImage = binErosion.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}