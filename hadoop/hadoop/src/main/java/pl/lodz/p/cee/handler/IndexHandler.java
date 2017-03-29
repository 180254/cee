package pl.lodz.p.cee.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class IndexHandler extends AbstractHandler {

    private final HandlerHelper handlerHelper = new HandlerHelper();

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        handlerHelper.serveResource(baseRequest, response, "index.html");
    }
}
