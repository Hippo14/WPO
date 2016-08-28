package Algorithm.image.bmp.write;

import Algorithm.image.bmp.BmpHeader;
import Algorithm.image.bmp.decode.LitEndInputStream;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MSI on 2016-08-28.
 */
public class Write {

    LitEndInputStream input;

    BmpHeader header;
    BufferedImage image;

    public Write(InputStream input) {
        this.input = createLittleEndianInputStream(input);
        this.header = createImageHeader(this.input);
    }

    private BmpHeader createImageHeader(LitEndInputStream input) throws IOException {
        return new BmpHeader(input);
    }

    private LitEndInputStream createLittleEndianInputStream(InputStream input) {
        return new LitEndInputStream(input);
    }

}
