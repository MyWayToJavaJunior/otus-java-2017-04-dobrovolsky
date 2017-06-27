package ru.otus.dobrovolsky.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.dbService.DBServiceHibernateImpl;
import ru.otus.dobrovolsky.servlet.AdminServlet;
import ru.otus.dobrovolsky.servlet.ExitServlet;
import ru.otus.dobrovolsky.servlet.LoginServlet;

public class Main {
    //49001—49150 free TCP-UDP ports
    private final static int PORT = 49094;
    private final static String PUBLIC_HTML = "public";

    public static void main(String[] args) throws Exception {
        DBService dbService = new DBServiceHibernateImpl();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(ExitServlet.class, "/exit");
        context.addServlet(new ServletHolder(new AdminServlet(dbService)), "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();

        Worker worker = new Worker(dbService);
        worker.run();

        server.join();

    }
}
