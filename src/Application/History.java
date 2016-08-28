package Application;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-05-12.
 */
public class History {

    BufferedImage bufferedImage;
    String description;
    int id;

    public History(BufferedImage bufferedImage, String description, int id) {
        this.bufferedImage = bufferedImage;
        this.description = description;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
