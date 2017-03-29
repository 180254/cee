package pl.lodz.p.cee.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HadoopHelper {

    public long currentTaskId = 0;

    // ---------------------------------------------------------------------------------------------------------------

    public Configuration newConfiguration() {
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        return conf;
    }

    // ---------------------------------------------------------------------------------------------------------------

    public long startTask(InputStream file) throws Exception {
        long taskId = System.currentTimeMillis();

        Configuration conf = newConfiguration();
        HadoopJob hadoopJob = new HadoopJob();

        uploadTaskFileToHDFS(conf, file, taskId);
        ToolRunner.run(conf, hadoopJob, new String[]{Long.toString(taskId)});

        currentTaskId = taskId;
        return taskId;
    }

    // ---------------------------------------------------------------------------------------------------------------

    public void uploadTaskFileToHDFS(Configuration conf, InputStream file, long taskId) throws IOException {
        Path directoryPath = new Path(String.format("/user/vagrant/input/%s", taskId));
        Path filePath = new Path(String.format("/user/vagrant/input/%s/data", taskId));

        FileSystem fs = FileSystem.get(conf);
        boolean mkdirs = fs.mkdirs(directoryPath);

        FSDataOutputStream os = fs.create(filePath);
        IOUtils.copyBytes(file, os, conf);
    }

    // ---------------------------------------------------------------------------------------------------------------

    public Map<String, String> readTaskResultsFromHDFS() throws IOException {
        Map<String, String> map = new HashMap<>();

        Configuration conf = newConfiguration();
        Path path = new Path(String.format("/user/vagrant/output/%s", currentTaskId));
        FileSystem fs = FileSystem.get(conf);

        RemoteIterator<LocatedFileStatus> files = fs.listFiles(path, false);
        while (files.hasNext()) {
            LocatedFileStatus nextFile = files.next();
            Path nextPath = nextFile.getPath();
            String nextName = nextPath.getName();

            byte[] nextBuffer = new byte[(int) nextFile.getLen()];
            FSDataInputStream nextIn = fs.open(nextPath);
            IOUtils.readFully(nextIn, nextBuffer, 0, nextBuffer.length);
            String nextString = new String(nextBuffer, StandardCharsets.UTF_8);

            map.put(nextName, nextString);
        }

        return map;
    }
}
