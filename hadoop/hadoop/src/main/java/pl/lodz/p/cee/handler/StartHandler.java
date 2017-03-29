package pl.lodz.p.cee.handler;

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
import java.io.PrintWriter;
import java.io.StringWriter;


public class StartHandler extends AbstractHandler {

    private final HadoopHelper hadoopHelper;
    private final HandlerHelper handlerHelper;

    public StartHandler(HadoopHelper hadoopHelper, HandlerHelper handlerHelper) {
        this.hadoopHelper = hadoopHelper;
        this.handlerHelper = handlerHelper;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String resource = "?!";

        baseRequest.setHandled(true);
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getMethod().equals("GET")) {
            resource = handlerHelper.resource("start.html");

        } else if (request.getMethod().equals("POST")) {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

            try (InputStream file = upload.parseRequest(request).get(0).getInputStream()) {
                long taskId = hadoopHelper.startTask(file);
                resource = handlerHelper.resource("start-ok.html");

            } catch (Exception e) {
                response.setContentType("text/plain; charset=utf-8");

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                resource = sw.toString();
            }
        }

        response.getWriter().write(resource);
    }
}
