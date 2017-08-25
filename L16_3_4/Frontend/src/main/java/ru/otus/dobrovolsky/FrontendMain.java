package ru.otus.dobrovolsky;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.dobrovolsky.frontend.server.FrontendServer;
import ru.otus.dobrovolsky.frontend.servlet.AdminByTimerServlet;
import ru.otus.dobrovolsky.frontend.servlet.AdminByWebSocketServlet;
import ru.otus.dobrovolsky.frontend.servlet.ExitServlet;
import ru.otus.dobrovolsky.frontend.servlet.LoginServlet;
import ru.otus.dobrovolsky.message.server.channel.SocketClientChannel;
import ru.otus.dobrovolsky.message.util.Utils;

import java.net.Socket;
import java.util.logging.Logger;

public class FrontendMain {
    private static final Logger LOGGER = Logger.getLogger(FrontendMain.class.getName());
    private final static String PUBLIC_HTML = "html";
    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    public static void main(String[] args) throws Exception {
        int port = Utils.getParameter(args, "-port");
        int num = Utils.getParameter(args, "-num");

        SocketClientChannel client = new SocketClientChannel(new Socket(HOST, PORT));
        client.init();

        FrontendServer frontendServer = new FrontendServer(num, client);
        frontendServer.start();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(".");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(FrontendMain.class.getClassLoader().getResource(PUBLIC_HTML).toExternalForm());

        context.addServlet(new ServletHolder(new AdminByWebSocketServlet(port, client, frontendServer)), "/admin_by_ws");
        context.addServlet(new ServletHolder(new AdminByTimerServlet(client, frontendServer)), "/admin_by_timer");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new ExitServlet()), "/exit");

        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));
        server.start();
        server.join();
    }
}
