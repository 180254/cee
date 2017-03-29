package pl.lodz.p.cee.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.io.InputStream;

public class HadoopHelper {

    public long startTask(InputStream file) throws Exception {
        long taskId = System.currentTimeMillis();

        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        conf.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );

        HadoopJob hadoopJob = new HadoopJob();

        uploadTaskFileToHDFS(conf, file, taskId);
        ToolRunner.run(conf, hadoopJob, new String[]{Long.toString(taskId)});

        return taskId;
    }

    public void uploadTaskFileToHDFS(Configuration conf, InputStream file, long taskId) throws IOException {
        Path directoryPath = new Path(String.format("/user/vagrant/input/%s", taskId));
        Path filePath = new Path(String.format("/user/vagrant/input/%s/data", taskId));

        FileSystem fs = FileSystem.get(conf);
        boolean mkdirs = fs.mkdirs(directoryPath);

        FSDataOutputStream os = fs.create(filePath);
        IOUtils.copyBytes(file, os, conf);
    }
}
