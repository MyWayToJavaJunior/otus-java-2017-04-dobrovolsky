package ru.otus.dobrovolsky.servlet.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import ru.otus.dobrovolsky.dbService.CacheDescriptor;
import ru.otus.dobrovolsky.main.Worker;

import java.io.IOException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

@WebSocket
public class AdminWebSocket {
    private Set<AdminWebSocket> users;
    private CacheDescriptor cacheDescriptor;
    private Session session;
    private Logger logger = Worker.getLogger();

    public AdminWebSocket(Set<AdminWebSocket> users, CacheDescriptor cacheDescriptor) {
        this.users = users;
        this.cacheDescriptor = cacheDescriptor;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        users.add(this);
        setSession(session);
        logger.info("onOpen");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cacheDescriptor.updateFields();

                try {
                    session.getRemote().sendString(new Gson().toJson(cacheDescriptor.getCacheMap()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 2500, 2500);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        users.remove(this);
        logger.info("onClose");
    }
}