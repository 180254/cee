package pl.lodz.p.cee.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import pl.lodz.p.cee.entropy.DistanceCalculatorImpl;
import pl.lodz.p.cee.entropy.EntropyCalculator;
import pl.lodz.p.cee.entropy.EntropyCalculatorImpl;

import java.io.IOException;

public class MyMapper extends Mapper<Object, Text, StockKey, IntWritable> {

    private final EntropyCalculator ec = new EntropyCalculatorImpl(
            new DistanceCalculatorImpl()
    );

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String password = value.toString();
        int entropy = ec.compute(password);

        context.write(
                new StockKey(password, entropy),
                new IntWritable(entropy)
        );
    }
}
