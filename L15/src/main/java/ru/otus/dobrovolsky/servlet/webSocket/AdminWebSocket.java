package ru.otus.dobrovolsky.servlet.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.dobrovolsky.base.messages.FrontendService;

import java.io.IOException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

@WebSocket
public class AdminWebSocket {
    private Set<AdminWebSocket> users;
    private Session session;
    private FrontendService frontendService;

    public AdminWebSocket(Set<AdminWebSocket> users, FrontendService frontendService) {
        this.users = users;
        this.frontendService = frontendService;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        users.add(this);
        setSession(session);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                frontendService.handleRequestCacheUpdate();

                try {
                    session.getRemote().sendString(new Gson().toJson(frontendService.handleRequestCacheStats()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 1000);
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
    }
}