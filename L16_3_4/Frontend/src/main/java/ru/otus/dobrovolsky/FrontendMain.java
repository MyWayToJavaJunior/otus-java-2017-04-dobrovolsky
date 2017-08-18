package ru.otus.dobrovolsky;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.dobrovolsky.frontend.servlet.AdminByTimerServlet;
import ru.otus.dobrovolsky.frontend.servlet.AdminByWebSocketServlet;
import ru.otus.dobrovolsky.frontend.servlet.ExitServlet;
import ru.otus.dobrovolsky.frontend.servlet.LoginServlet;
import ru.otus.dobrovolsky.message.Msg;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;
import ru.otus.dobrovolsky.message.server.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendMain {
    private static final Logger LOGGER = Logger.getLogger(FrontendMain.class.getName());
//    private final static String PUBLIC_HTML = "./Frontend/public";
    private final static String PUBLIC_HTML = "public";
    private static Map<String, Object> cacheMap = new HashMap<>();
    private final String HOST = "localhost";
    private final int PORT = 5050;

    public static void main(String[] args) throws Exception {
        int port = getPort(args);
        new FrontendMain().start(new Address("Frontend"), port);
//        new FrontendMain().start(new Address("Frontend"), 49095);
    }

    public static Integer getPort(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-port")) {
                return Integer.valueOf(args[i + 1]);
            }
        }
        throw new RuntimeException();
    }

    public static Map<String, Object> getCacheMap() {
        return cacheMap;
    }

    private void start(Address address, int port) throws Exception {
        LOGGER.log(Level.SEVERE, "Started on port: " + port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(".");

        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase(PUBLIC_HTML);
        resourceHandler.setResourceBase(FrontendMain.class.getClassLoader().getResource(PUBLIC_HTML).toExternalForm());

        SocketClientChannel client = new SocketClientChannel(new Socket(HOST, PORT));
        client.init();
        client.send(new MsgRegistration(address, new Address("MessageServerService")));

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            try {
                while (true) {
                    Msg receivedMsg = client.take();
                    LOGGER.info("RECEIVED MESSAGE:  " + receivedMsg.getClass() + "   from:   " + receivedMsg.getFrom() + " to:   " + receivedMsg.getTo());
                    if (receivedMsg.getClass().getName().equals(MsgRegistrationAnswer.class.getName())) {
                        LOGGER.info("Registered on MessageServer successfully");
                    }
                    if (receivedMsg.getClass().getName().equals(MsgGetCacheAnswer.class.getName())) {
                        LOGGER.info("Getting cache information");
                        cacheMap = receivedMsg.getValue();
                    }
                    if (receivedMsg.getClass().getName().equals(MsgUpdateCacheAnswer.class.getName())) {
                        LOGGER.info("Cache info updated successfully");
                    }
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });

        client.send(new MsgUpdateCache(address, new Address("DBServer")));
        client.send(new MsgGetCache(address, new Address("DBServer")));

        context.addServlet(new ServletHolder(new AdminByWebSocketServlet(port, client)), "/admin_by_ws");
        context.addServlet(new ServletHolder(new AdminByTimerServlet(client)), "/admin_by_timer");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new ExitServlet()), "/exit");

        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
