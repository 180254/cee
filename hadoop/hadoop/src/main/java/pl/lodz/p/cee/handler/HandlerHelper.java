package pl.lodz.p.cee.handler;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class HandlerHelper {

    public void serveResource(Request baseRequest, HttpServletResponse response, String name) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String html = readResource(name);

        response.getWriter().write(html);
        baseRequest.setHandled(true);
    }

    public String readResource(String name) throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/" + name);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            return br.lines().collect(Collectors.joining("\n"));
        }
    }
}
