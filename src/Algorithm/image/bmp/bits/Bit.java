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

    protected static byte setBit(byte b, int i, int index) {
        if (index == 0) {
            b &= ~(1 << (7 - i));
        }
        else {
            b |= 1 << (7 - i);
        }
        return b;
    }

    protected static byte setNibble(byte b, int i, int index) {
        return b |= (index << ((1 - i) * 4));
    }

}
