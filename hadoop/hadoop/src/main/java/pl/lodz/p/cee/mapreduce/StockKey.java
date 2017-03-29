package pl.lodz.p.cee.mapreduce;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

import javax.annotation.Nonnull;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Comparator;

public class StockKey implements WritableComparable<StockKey> {

    private String key;
    private int value;

    public StockKey() {
    }

    public StockKey(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        WritableUtils.writeString(out, key);
        WritableUtils.writeVInt(out, value);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.key = WritableUtils.readString(in);
        this.value = WritableUtils.readVInt(in);
    }

    @Override
    public int compareTo(@Nonnull StockKey o) {
        return Comparator
                .comparing((StockKey p) -> -p.value)
                .thenComparing(p -> p.key)
                .compare(this, o);
    }
}
