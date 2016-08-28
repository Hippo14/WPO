package Algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysztof Macioszek
 */
public class MaskArray {

    private List<List<Integer>> maskArray;
    private int size;

    public MaskArray(int size) {
        this.maskArray = new ArrayList<>();
        this.size = size;
        setMaskTemplate();
    }

    public Integer[][] getMaskArray() {
        Integer[][] array = new Integer[maskArray.size()][];
        for (int i = 0; i < maskArray.size(); i++) {
            List<Integer> row = maskArray.get(i);
            array[i] = row.toArray(new Integer[row.size()]);
        }

        return array;
    }

    private void setMaskTemplate() {
        for (int i = 0; i < size; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                list.add(1);
            }
            maskArray.add(list);
        }
    }

}