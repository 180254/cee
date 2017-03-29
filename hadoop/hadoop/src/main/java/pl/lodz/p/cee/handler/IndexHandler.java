package pl.lodz.p.cee.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class IndexHandler extends AbstractHandler {

    private final HandlerHelper handlerHelper;

    public IndexHandler(HandlerHelper handlerHelper) {
        this.handlerHelper = handlerHelper;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String resource = handlerHelper.resource("index.html");

        baseRequest.setHandled(true);
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(resource);
    }
}
