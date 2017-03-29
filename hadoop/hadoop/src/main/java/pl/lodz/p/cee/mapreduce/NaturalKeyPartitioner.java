package pl.lodz.p.cee.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class NaturalKeyPartitioner extends Partitioner<StockKey, IntWritable> {

    @Override
    public int getPartition(StockKey key, IntWritable val, int numPartitions) {
        int hash = key.getKey().hashCode();
        return hash % numPartitions;
    }
}
