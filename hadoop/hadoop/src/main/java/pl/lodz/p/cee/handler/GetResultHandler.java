package pl.lodz.p.cee.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import pl.lodz.p.cee.hadoop.HadoopHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class GetResultHandler extends AbstractHandler {

    private final HadoopHelper hadoopHelper;

    public GetResultHandler(HadoopHelper hadoopHelper) {
        this.hadoopHelper = hadoopHelper;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        baseRequest.setHandled(true);
        response.setContentType("text/plain; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = response.getWriter();
        writer.write("========== TASK=" + hadoopHelper.currentTaskId + "\r\n");

        try {
            Map<String, String> taskResults = hadoopHelper.readTaskResultsFromHDFS();
            for (Map.Entry<String, String> taskResult : taskResults.entrySet()) {
                String fileName = taskResult.getKey();
                String fileContent = taskResult.getValue();

                writer.write("========== FILE=" + fileName + "\r\n");
                if (!fileContent.isEmpty()) {
                    writer.write(fileContent + "\r\n");
                }
            }

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            writer.write(sw.toString());
        }

    }
}
