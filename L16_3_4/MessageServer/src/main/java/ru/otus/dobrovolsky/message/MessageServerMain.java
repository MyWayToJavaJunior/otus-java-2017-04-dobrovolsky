package ru.otus.dobrovolsky.message;

import ru.otus.dobrovolsky.message.runner.ProcessRunnerImpl;
import ru.otus.dobrovolsky.message.server.MessageServer;
import ru.otus.dobrovolsky.message.server.services.MessageServerService;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServerMain {
    private static final Logger LOGGER = Logger.getLogger(MessageServerMain.class.getName());

    private static final String START_COMMAND = "java -jar ";
    private static final int CLIENT_START_DELAY_SEC = 3;
    private static MessageServer messageServer;
    private static MessageServerService serverService;

    public static void main(String[] args) throws Exception {
        new MessageServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        startClient(executorService, "./DBServer/target/DBServer.jar");
//        startClient(executorService, "./DBServer/target/DBServer.jar");
//        startClient(executorService, "./Frontend/target/Frontend.jar -port 49094");
        startClient(executorService, "./Frontend/target/Frontend.jar -port 49095");
//        startClient(executorService, "./Frontend/target/Frontend-jar-with-dependencies.jar -port 49095");

        messageServer = new MessageServer();
        serverService = MessageServerService.getInstance();
        messageServer.start();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                LOGGER.info("STARTING:  " + command);
                new ProcessRunnerImpl().start(START_COMMAND + command);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}
