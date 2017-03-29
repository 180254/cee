package pl.lodz.p.cee.mapreduce;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class CompositeKeyComparator extends WritableComparator {

    protected CompositeKeyComparator() {
        super(StockKey.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        StockKey k1 = (StockKey) w1;
        StockKey k2 = (StockKey) w2;

        int result = Integer.compare(k1.getValue(), k2.getValue());
        if (result == 0) {
            result = k1.getKey().compareTo(k2.getKey());
        }

        return result;
    }
}
