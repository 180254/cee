package pl.lodz.p.cee.mapreduce;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

import javax.annotation.Nonnull;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StockKey implements WritableComparable<StockKey> {

    private String key;
    private int value;


    public StockKey() {
    }

    public StockKey(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(@Nonnull StockKey o) {
        int result = Integer.compare(getValue(), o.getValue());
        if (result == 0) {
            result = getKey().compareTo(o.getKey());
        }

        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        WritableUtils.writeString(out, key);
        out.writeInt(value);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.key = WritableUtils.readString(in);
        this.value = in.readInt();
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
}
