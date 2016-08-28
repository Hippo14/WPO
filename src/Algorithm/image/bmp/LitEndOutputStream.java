package Algorithm.image.bmp;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by KMacioszek on 2016-08-11.
 */
public class LitEndOutputStream extends FilterOutputStream {

    protected int written;

    public LitEndOutputStream(OutputStream out) {
        super(out);
    }

    public void writeInteger(int i) throws IOException {
        out.write(i & 0xFF);
        out.write((i >>> 8) & 0xFF);
        out.write((i >>> 16) & 0xFF);
        out.write((i >>> 24) & 0xFF);
        written += 4;
    }

    public void writeShort(int s) throws IOException {
        out.write(s & 0xFF);
        out.write((s >>> 8) & 0xFF);
        written += 2;

    }

    public void writeByte(int b) throws IOException {
        out.write(b);
        written++;
    }

}




//public class LitEndOutputStream extends DataOutputStream {
//
//    public LitEndOutputStream(OutputStream out) {
//        super(out);
//    }
//
//    public void writeShort(short value) throws IOException {
////        out.write(value & 0xFF);
////        out.write((value >>> 8) & 0xFF);
//
//        int byte1 = value & 0xFF;
//        int byte2 = (value >>> 8) & 0xFF;
//
//        super.writeShort(byte1 | byte2);
//    }
//
//    public void writeInteger(int value) throws IOException {
////        out.write(value & 0xFF);
////        out.write((value >>> 8) & 0xFF);
////        out.write((value >>> 16) & 0xFF);
////        out.write((value >>> 24) & 0xFF);
//
//        int byte1 = value & 0xFF;
//        int byte2 = (value >>> 8) & 0xFF;
//        int byte3 = (value >>> 16) & 0xFF;
//        int byte4 = (value >>> 24) & 0xFF;
//
//        super.writeInt(byte1 | byte2 | byte3 | byte4);
//    }
//}
