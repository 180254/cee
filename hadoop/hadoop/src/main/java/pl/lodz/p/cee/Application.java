package pl.lodz.p.cee;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import pl.lodz.p.cee.hadoop.HadoopHelper;
import pl.lodz.p.cee.handler.GetResultHandler;
import pl.lodz.p.cee.handler.HandlerHelper;
import pl.lodz.p.cee.handler.IndexHandler;
import pl.lodz.p.cee.handler.StartHandler;

public class Application {

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        Server server = new Server(9000);

        HadoopHelper hadoopHelper = new HadoopHelper();
        HandlerHelper handlerHelper = new HandlerHelper();

        ContextHandler contextIndex = new ContextHandler("/");
        contextIndex.setHandler(new IndexHandler(handlerHelper));

        ContextHandler contextStart = new ContextHandler("/start");
        contextStart.setAllowNullPathInfo(true);
        contextStart.setHandler(new StartHandler(hadoopHelper, handlerHelper));

        ContextHandler contextGetResult = new ContextHandler("/getresult");
        contextGetResult.setHandler(new GetResultHandler(hadoopHelper));

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{contextIndex, contextStart, contextGetResult});

        server.setHandler(contexts);

        server.start();
        server.join();
    }
}
