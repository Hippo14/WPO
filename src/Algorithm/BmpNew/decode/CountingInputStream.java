package Algorithm.BmpNew.decode;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KMacioszek on 2016-08-11.
 */
public class CountingInputStream extends FilterInputStream {

    int count;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int read() throws IOException {
        int result = super.read();
        if (result != -1)
            count++;
        return result;
    }

    @Override
    public int read(byte[] bytes, int offset, int lenght) throws IOException {
        int result = super.read(bytes, offset, lenght);
        if (result > 0)
            count += result;
        return result;
    }
}
