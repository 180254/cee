package pl.lodz.p.cee.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MyReducer extends Reducer<StockKey, IntWritable, Text, Text> {

    @Override
    protected void reduce(StockKey key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        for (IntWritable value : values) {
            context.write(
                    new Text(key.getKey()),
                    new Text(value.toString())
            );
        }
    }
}
