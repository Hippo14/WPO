package Algorithm.utils.exceptions;

import java.io.IOException;

/**
 * Created by MSI on 2016-08-28.
 */
public class UnknownFormatException extends IOException {

    public UnknownFormatException() {

    }

    public UnknownFormatException(String message) {
        super(message);
    }

}
