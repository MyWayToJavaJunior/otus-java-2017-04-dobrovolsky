package ru.otus.dobrovolsky.backend;

import ru.otus.dobrovolsky.backend.server.DBServer;

import java.util.logging.Logger;

public class DBServerMain {
    private static final Logger LOGGER = Logger.getLogger(DBServerMain.class.getName());

    public static void main(String[] args) throws Exception {
        int num = getArgument(args);

        DBServer dbServer = new DBServer(num);
        dbServer.start();
    }

    public static Integer getArgument(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-num")) {
                return Integer.valueOf(args[i + 1]);
            }
        }
        throw new RuntimeException();
    }
}
