package ru.otus.dobrovolsky.frontend.servlet.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.dobrovolsky.FrontendMain;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;
import ru.otus.dobrovolsky.message.server.Address;
import ru.otus.dobrovolsky.message.server.MsgGetCache;
import ru.otus.dobrovolsky.message.server.MsgUpdateCache;

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

    public AdminWebSocket(Set<AdminWebSocket> users, SocketClientChannel client) {
        this.users = users;
        this.client = client;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws InterruptedException {
        users.add(this);
        setSession(session);

//        while (true) {
//            client.send(new MsgUpdateCache(new Address("Frontend"), new Address("DBServer")));
//            Thread.sleep(2500);
//            client.send(new MsgGetCache(new Address("Frontend"), new Address("DBServer")));
//            Thread.sleep(2500);
//
//            try {
////                    session.getRemote().sendString(new Gson().toJson(port));
//                session.getRemote().sendString(new Gson().toJson(FrontendMain.getCacheMap()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Thread.sleep(2500);
//        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                client.send(new MsgUpdateCache(new Address("Frontend"), new Address("DBServer")));
                client.send(new MsgGetCache(new Address("Frontend"), new Address("DBServer")));

                try {
//                    session.getRemote().sendString(new Gson().toJson(port));
                    session.getRemote().sendString(new Gson().toJson(FrontendMain.getCacheMap()));
                    LOGGER.info("       FrontendMain.getCacheMap()" + FrontendMain.getCacheMap());
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