package ru.otus.dobrovolsky.frontend.servlet.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.dobrovolsky.frontend.server.FrontendServer;
import ru.otus.dobrovolsky.message.server.channel.SocketClientChannel;
import ru.otus.dobrovolsky.message.server.messages.MsgCache;

import java.io.IOException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

@WebSocket
public class AdminWebSocket {
    private static final Logger LOGGER = Logger.getLogger(AdminWebSocket.class.getName());
    private final SocketClientChannel client;
    private Set<AdminWebSocket> users;
    private Session session;
    private FrontendServer frontendServer;

    public AdminWebSocket(Set<AdminWebSocket> users, SocketClientChannel client, FrontendServer frontendServer) {
        this.users = users;
        this.client = client;
        this.frontendServer = frontendServer;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws InterruptedException {
        users.add(this);
        setSession(session);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                client.send(new MsgCache(frontendServer.getFrontendAddress(), frontendServer.getDBServerAddress(), null));

                try {
                    session.getRemote().sendString(new Gson().toJson(frontendServer.getCacheMap()));
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
    }
}