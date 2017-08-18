package ru.otus.dobrovolsky.message.server;

import ru.otus.dobrovolsky.message.Msg;
import ru.otus.dobrovolsky.message.channel.MsgChannel;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class MessageServer {
    private static final Logger LOGGER = Logger.getLogger(MessageServer.class.getName());

    private static final int THREADS_NUMBER = 4;
    private static final int PORT = 5050;
    private static final int DELAY = 250;

    private final ExecutorService executor;
    private final Map<Address, MsgChannel> channelAddresses;
    private final List<MsgChannel> channels;
    private final Map<MsgChannel, Address> connectionMap;

    public MessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        channels = new ArrayList<>();
        channelAddresses = new HashMap<>();
        this.connectionMap = new HashMap<>();
    }

    public void start() throws Exception {
        executor.submit(this::getMessage);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket client = serverSocket.accept();
                SocketClientChannel channel = new SocketClientChannel(client);
                channel.init();
                channels.add(channel);
                channel.addShutdownRegistration(() -> connectionMap.remove(channel));
                connectionMap.put(channel, null);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void getMessage() {
        while (true) {
            for (Map.Entry<MsgChannel, Address> entry : connectionMap.entrySet()) {
                MsgChannel channel = entry.getKey();
                Msg message = channel.poll();
                if (message != null) {
                    LOGGER.info("Received the message from: " + message.getFrom() + " to:   " + message.getTo() + " " +
                            "message:   " + message);
                    if (message.getClass().getName().equals(MsgRegistration.class.getName())) {
                        LOGGER.info("Sending registration answer message: " + message.getFrom() + " to:   " + message
                                .getTo() + " " +
                                "message:   " + message);
                        connectionMap.put(channel, message.getFrom());
                        channel.send(new MsgRegistrationAnswer(message.getTo(), message
                                .getFrom()));
                    } else {
                        LOGGER.info("Sending message:    " + message.getClass().getName() + " from:   " + message
                                .getFrom() + "   to:    " + message.getTo());
                        getChannel(message.getTo()).send(message);
                    }
                }
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private MsgChannel getChannel(Address address) {
        for (Map.Entry<MsgChannel, Address> entry : connectionMap.entrySet()) {
            if (entry.getValue().equals(address)) {
                LOGGER.info("       Addressee TO:   " + entry.getKey());
                return entry.getKey();
            }
        }
        return null;
    }
}
