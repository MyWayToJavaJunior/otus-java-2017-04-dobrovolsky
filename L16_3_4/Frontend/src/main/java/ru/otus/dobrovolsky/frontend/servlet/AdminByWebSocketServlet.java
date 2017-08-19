package ru.otus.dobrovolsky.frontend.servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.dobrovolsky.frontend.server.FrontendServer;
import ru.otus.dobrovolsky.frontend.servlet.webSocket.AdminWebSocketCreator;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AdminByWebSocketServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private static final String ADMIN_PAGE_TEMPLATE = "admin_by_ws.html";
    private static final Logger LOGGER = Logger.getLogger(AdminByWebSocketServlet.class.getName());
    private final SocketClientChannel client;
    private final int port;
    private Map<String, Object> pageVariables = new HashMap<>();
    private FrontendServer frontendServer;

    public AdminByWebSocketServlet(int port, SocketClientChannel client, FrontendServer frontendServer) {
        this.port = port;
        this.client = client;
        this.frontendServer = frontendServer;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new AdminWebSocketCreator(client, frontendServer));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        pageVariables = createPageVariablesMap(port);

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().println(TemplateProcessor.getInstance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> createPageVariablesMap(int port) {
        pageVariables.put("port", String.valueOf(port));

        return pageVariables;
    }
}
