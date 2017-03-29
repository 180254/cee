package pl.lodz.p.cee;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import pl.lodz.p.cee.mapreduce.EntropyMapper;
import pl.lodz.p.cee.mapreduce.SingleIntegerReducer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();


        Job job = Job.getInstance(conf);
        job.setJarByClass(Application.class);
        job.setMapperClass(EntropyMapper.class);
        job.setCombinerClass(SingleIntegerReducer.class);
        job.setReducerClass(SingleIntegerReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("/user/vagrant/input/")); // existing HDFS directory
        FileOutputFormat.setOutputPath(job, new Path("/user/vagrant/output/" + System.currentTimeMillis() +"/")); // not existing HDFS directory

        job.submit();
    }
}
