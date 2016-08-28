package Algorithm.oper_geometr;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * @author Krzysztof Macioszek
 */
public class RotateImage {

    int angle;
    BufferedImage bufferedImage;

    public RotateImage(BufferedImage bufferedImage, int angle) {
        this.bufferedImage = bufferedImage;
        this.angle = angle;
    }

    public BufferedImage rotate() {
        AffineTransform at = new AffineTransform();

        at.rotate(Math.toRadians(angle), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        return op.filter(bufferedImage, null);
    }

}
