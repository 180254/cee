package pl.lodz.p.cee;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import pl.lodz.p.cee.handler.GetResultHandler;
import pl.lodz.p.cee.handler.IndexHandler;
import pl.lodz.p.cee.handler.StartHandler;

public class Application {

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        Server server = new Server(8080);

        ContextHandler contextIndex = new ContextHandler("/");
        contextIndex.setHandler(new IndexHandler());

        ContextHandler contextStart = new ContextHandler("/start");
        contextStart.setAllowNullPathInfo(true);
        contextStart.setHandler(new StartHandler());

        ContextHandler contextGetResult = new ContextHandler("/getresult");
        contextGetResult.setHandler(new GetResultHandler());


        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{contextIndex, contextStart, contextGetResult});

        server.setHandler(contexts);

        server.start();
        server.join();
    }

//        Configuration conf = new Configuration();
//
//        Job job = Job.getInstance(conf);
//
//        job.setJarByClass(Application.class);
//
//        job.setPartitionerClass(MyPartitioner.class);
//        job.setGroupingComparatorClass(MyGroupingComparator.class);
//        job.setSortComparatorClass(MySortComparator.class);
//
//        job.setMapOutputKeyClass(StockKey.class);
//        job.setMapOutputValueClass(IntWritable.class);
//
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(Text.class);
//
//        job.setInputFormatClass(TextInputFormat.class);
//        job.setOutputFormatClass(TextOutputFormat.class);
//
//        job.setMapperClass(MyMapper.class);
//        job.setReducerClass(MyReducer.class);
//
//        FileInputFormat.addInputPath(job, new Path("/user/vagrant/input/")); // existing HDFS directory
//        FileOutputFormat.setOutputPath(job, new Path("/user/vagrant/output/" + System.currentTimeMillis() + "/")); // not existing HDFS directory
//
//        job.submit();
}
