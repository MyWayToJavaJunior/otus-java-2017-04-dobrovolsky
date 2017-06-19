package ru.otus.dobrovolsky.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
import ru.otus.dobrovolsky.base.dataSets.DataSet;
import ru.otus.dobrovolsky.base.dataSets.PhoneDataSet;
import ru.otus.dobrovolsky.base.dataSets.UserDataSet;
import ru.otus.dobrovolsky.dbService.dao.DataSetDAO;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "test"); //default login, allowed value - tully
        configuration.setProperty("hibernate.connection.password", "test"); //default password, allowed value - tully
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    public <T extends DataSet> void save(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            DataSetDAO dao = new DataSetDAO(session);
            dao.save(dataSet);
        }
    }

    public <T extends DataSet> T read(Class<T> clazz, long id) {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.read(clazz, id);
        });
    }

    public <T extends DataSet> T readByName(Class<T> clazz, String name) {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.readByName(clazz, name);
        });
    }

    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.readAll(clazz);
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}