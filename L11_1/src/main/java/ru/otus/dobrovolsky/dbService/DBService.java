package ru.otus.dobrovolsky.dbService;

import ru.otus.dobrovolsky.cache.Element;
import ru.otus.dobrovolsky.cache.MyCacheEngineImpl;
import ru.otus.dobrovolsky.dbService.dao.UsersDAO;
import ru.otus.dobrovolsky.dbService.dataSets.DataSet;
import ru.otus.dobrovolsky.dbService.dataSets.User;
import ru.otus.dobrovolsky.reflect.PackageMetaData;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class DBService {
    private final Connection connection;
    private PackageMetaData pmd;
    private MyCacheEngineImpl<Long, ? extends DataSet> cache;

    public DBService() throws Exception {
        connection = getConnection();
        pmd = PackageMetaData.getInstance();
        pmd.parsePackage("ru.otus");
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable(User.class);
            dao.createTable(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DBService(String packageName) throws Exception {
        connection = getConnection();
        pmd = PackageMetaData.getInstance();
        pmd.parsePackage(packageName);
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable(User.class);
            System.out.println("droped!");
            dao.createTable(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws DBException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://" +       //db type
                    "localhost:" +               //host name
                    "3306/" +                    //default port
                    "db_example?" +              //db name
                    "useSSL=false&" +            //do not use ssl
                    "user=test&" +              //default login, allowed value - tully
                    "password=test";            //default password, allowed value - tully

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable(User.class);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name:        " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version:     " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver:         " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit:     " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public User loadUser(long id) throws DBException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        User user = getFromCache(id);

        if (user != null) {
            return user;
        }
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);

            user = dao.loadById(id, User.class);
            if (cache != null) {
                cache.put(id, new Element(user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public <T extends DataSet> void saveUser(T dataSet) throws Exception {
        UsersDAO dao = new UsersDAO(connection);
        try {
            connection.setAutoCommit(false);
            dao.saveUser(dataSet);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> void createTable(Class<T> clazz) throws IllegalAccessException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            connection.setAutoCommit(false);
            dao.createTable(clazz);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerCache(MyCacheEngineImpl<Long, ? extends DataSet> cache) {
        this.cache = cache;
    }

    @SuppressWarnings("unchecked")
    private <T extends DataSet> T getFromCache(long id) {
        T cachedDataSet = null;
        if (cache != null) {
            Element element = cache.get(id);
            if (element != null) {
                cachedDataSet = (T) element.getValue();
            }
        }
        return cachedDataSet;
    }
}
