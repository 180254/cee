package pl.lodz.p.cee.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import pl.lodz.p.cee.mapreduce.*;

public class HadoopJob extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        String taskId = args[0];
        Configuration conf = this.getConf();

        Job job = Job.getInstance(conf);

        job.setJarByClass(HadoopJob.class);

        job.setPartitionerClass(MyPartitioner.class);
        job.setGroupingComparatorClass(MyGroupingComparator.class);
        job.setSortComparatorClass(MySortComparator.class);

        job.setMapOutputKeyClass(StockKey.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        FileInputFormat.addInputPath(job, new Path(String.format("/user/vagrant/input/%s/", taskId)));
        FileOutputFormat.setOutputPath(job, new Path(String.format("/user/vagrant/output/%s/", taskId)));

        job.submit();
        return 0;
    }
}
