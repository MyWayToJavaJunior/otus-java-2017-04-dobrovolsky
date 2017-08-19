package ru.otus.dobrovolsky.message;

import ru.otus.dobrovolsky.message.runner.ProcessRunnerImpl;
import ru.otus.dobrovolsky.message.server.MsgServer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//import ru.otus.dobrovolsky.message.server.services.MsgServerService;

public class MsgServerMain {
    private static final Logger LOGGER = Logger.getLogger(MsgServerMain.class.getName());

    private static final int CLIENT_START_DELAY_SEC = 2;
    private static MsgServer msgServer;
//    private static MsgServerService serverService;
    private int initialFrontPort = 49094;

    public static void main(String[] args) throws Exception {
        int servicesToStart = getArgument(args);

        new MsgServerMain().start(servicesToStart);
    }

    public static Integer getArgument(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-count")) {
                return Integer.valueOf(args[i + 1]);
            }
        }
        throw new RuntimeException();
    }

    private void start(int servicesToStart) throws Exception {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(servicesToStart * 2);

        for (int i = 0; i < servicesToStart; i++) {
            startClient(executorService, "java -jar DBServer/target/DBServer.jar -num " + i);
            startClient(executorService, "java -jar Frontend/target/Frontend.jar -num " + i + " -port " + String.valueOf(initialFrontPort));
            initialFrontPort++;
        }

        msgServer = new MsgServer();
//        serverService = MsgServerService.getInstance();
        msgServer.start();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                LOGGER.info("STARTING:  " + command);
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}
