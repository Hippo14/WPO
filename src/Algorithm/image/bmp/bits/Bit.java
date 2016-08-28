package Algorithm.image.bmp.bits;

/**
 * Created by MSI on 2016-08-28.
 */
public class Bit {

    protected static int getBit(int n, int i) {
        return (n >> (7 - i)) & 1;
    }

    protected static int getNibble(int n, int i) {
        return (n >> (4 * (1 - i))) & 0xF;
    }

}
