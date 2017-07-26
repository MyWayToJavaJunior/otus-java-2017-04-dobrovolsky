package ru.otus.dobrovolsky.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MessageSystem {
    private static final int DEFAULT_STEP_TIME = 10;

    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap = new HashMap<>();
    private final Map<Address, Addressee> addresseeMap = new HashMap<>();

    private final Logger LOGGER = LoggerFactory.getLogger(MessageSystem.class);

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            new Thread(() -> {
                while (true) {
                    ConcurrentLinkedQueue<Message> queue = messagesMap.get(entry.getKey());
                    while (!queue.isEmpty()) {
                        LOGGER.info("Queue size:" + queue.size());
                        Message message = queue.poll();
                        message.exec(entry.getValue());
                        LOGGER.info(LocalDateTime.now() + ":    Message from:" + message.getFrom() + ", to:" + message
                                .getTo());
                    }
                    try {
                        Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
