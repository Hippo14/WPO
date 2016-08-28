package Algorithm.image.bmp;

import Algorithm.image.bmp.read.LitEndInputStream;

import java.io.IOException;

/**
 * Created by KMacioszek on 2016-08-11.
 */
public class Pixel {

    int reserved;
    int red;
    int green;
    int blue;

    public Pixel(LitEndInputStream litEndInputStream) throws IOException {
        blue = litEndInputStream.readUnsignedByte();
        green = litEndInputStream.readUnsignedByte();
        red = litEndInputStream.readUnsignedByte();
        reserved = litEndInputStream.readUnsignedByte();
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
