package pl.lodz.p.cee.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import pl.lodz.p.cee.entropy.EntropyCalculator;
import pl.lodz.p.cee.entropy.EntropyCalculatorImpl;

import java.io.IOException;

public class EntropyMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final EntropyCalculator ec = new EntropyCalculatorImpl();

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        int entropy = ec.compute(value.toString());
        context.write(value, new IntWritable(entropy));
    }
}
