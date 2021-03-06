package ru.otus.dobrovolsky.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.stat.Statistics;
import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.CacheDescriptor;
import ru.otus.dobrovolsky.base.messages.MessageSystemContext;
import ru.otus.dobrovolsky.dataSet.AddressDataSet;
import ru.otus.dobrovolsky.dataSet.PhoneDataSet;
import ru.otus.dobrovolsky.dataSet.UserDataSet;
import ru.otus.dobrovolsky.dbService.dao.DataSetDAO;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.Addressee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService, Addressee {
    private final Address address;
    private final MessageSystemContext context;
    private SessionFactory sessionFactory;
    private Statistics statistics;
    private Map<String, Object> config;
    private CacheDescriptor cacheDescriptor;

    public DBServiceHibernateImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);

        initializeConfiguration();
    }

    private void initializeConfiguration() {

        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

        config = new HashMap<>();
        prepareHibernate(config);
        preparePool(config);
        prepareCache(config);

        registryBuilder.applySettings(config);
        ServiceRegistry registry = registryBuilder.build();
        MetadataSources sources = new MetadataSources(registry)
                .addAnnotatedClass(PhoneDataSet.class)
                .addAnnotatedClass(UserDataSet.class)
                .addAnnotatedClass(AddressDataSet.class);

        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

        configureStatistics();

        getStatistics().setStatisticsEnabled(true);

        cacheDescriptor = new CacheDescriptor(context, address, this);
    }

    public MessageSystemContext getContext() {
        return context;
    }

    private void configureStatistics() {
        statistics = sessionFactory.getStatistics();
    }

    private void prepareCache(Map<String, Object> config) {
        config.put(Environment.USE_SECOND_LEVEL_CACHE, true);
        config.put(Environment.USE_QUERY_CACHE, true);

        config.put("hibernate.cache.use_structured_entries", true);

        config.put(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        config.put("net.sf.ehcache.configurationResourceName", "ehcache.xml");
    }

    private void preparePool(Map<String, Object> config) {
        config.put(Environment.C3P0_MIN_SIZE, 3);
        config.put(Environment.C3P0_MAX_SIZE, 12);
        config.put(Environment.C3P0_ACQUIRE_INCREMENT, 2);
        config.put(Environment.C3P0_TIMEOUT, 600);
    }

    private void prepareHibernate(Map<String, Object> config) {
        config.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        config.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        config.put(Environment.URL, "jdbc:mysql://localhost:3306/db_example");
        config.put(Environment.USER, "test");
        config.put(Environment.PASS, "test");
        config.put(Environment.HBM2DDL_AUTO, "create");
        config.put(Environment.SHOW_SQL, true);
        config.put(Environment.FORMAT_SQL, true);
        config.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, true);
    }

    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    public void save(UserDataSet dataSet) {
        DataSetDAO dao;
        try (Session session = sessionFactory.openSession()) {
            dao = new DataSetDAO(session);
            dao.save(dataSet);
        }
    }

    public UserDataSet read(long id) {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.read(id);
        });
    }

    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.readByName(name);
        });
    }

    public AddressDataSet readAddressById(long id) {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.readAddressById(id);
        });
    }

    public UserDataSet readByNamedQuery(long id) {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.readByNamedQuery(id);
        });
    }

    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            DataSetDAO dao = new DataSetDAO(session);
            return dao.readAll();
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

    public Map<String, Object> getCacheMap() {
        return cacheDescriptor.getCacheMap();
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public CacheDescriptor getCacheDescriptor() {
        return cacheDescriptor;
    }

    @Override
    public Address getAddress() {
        return address;
    }
}