package pl.lodz.p.cee.handler;

import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import pl.lodz.p.cee.hadoop.HadoopHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


public class StartHandler extends AbstractHandler {

    private final HadoopHelper hadoopHelper = new HadoopHelper();
    private final HandlerHelper handlerHelper = new HandlerHelper();

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (request.getMethod().equals("GET")) {
            handlerHelper.serveResource(baseRequest, response, "start.html");

        } else if (request.getMethod().equals("POST")) {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

            try (InputStream file = upload.parseRequest(request).get(0).getInputStream()) {
                hadoopHelper.startTask(file);
            } catch (Exception e) {
                throw new IOException(e);
            }

            handlerHelper.serveResource(baseRequest, response, "start-ok.html");
        }
    }
}
