package ru.otus.dobrovolsky.dbService;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
import ru.otus.dobrovolsky.base.dataSets.PhoneDataSet;
import ru.otus.dobrovolsky.base.dataSets.UserDataSet;
import ru.otus.dobrovolsky.dbService.dao.DataSetDAO;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private SessionFactory sessionFactory;
    private Statistics statistics;
    private Map<String, Object> config;

    public DBServiceHibernateImpl() {
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

        statistics = sessionFactory.getStatistics();

        statistics.setStatisticsEnabled(true);

        registerCacheMBean();
    }

    private void registerCacheMBean() {
        CacheManager manager = CacheManager.getCacheManager(CacheManager.DEFAULT_NAME);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ManagementService.registerMBeans(manager, mBeanServer, false, false, true, true);
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
        try (Session session = sessionFactory.openSession()) {
            DataSetDAO dao = new DataSetDAO(session);
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

    public void getQueryStatistics() {
        System.out.println("QueryStatistics: " + statistics.getQueryStatistics("org.hibernate.cache.ehcache" +
                ".EhCacheRegionFactory"));
    }

    public void getQueries() {
        System.out.println("Queries: ");
        String[] queries = statistics.getQueries();
        for (String q : queries) {
            System.out.println("    " + q);
        }
    }

    public void getQueryCacheHitCount() {
        System.out.println("QueryCacheHitCount: " + statistics.getQueryCacheHitCount());
    }

    public void getQueryCacheMissCount() {
        System.out.println("QueryCacheMissCount: " + statistics.getQueryCacheMissCount());
    }

    public void getSecondLevelCacheMissCount() {
        System.out.println("SecondLevelCacheMissCount:  " + statistics.getSecondLevelCacheMissCount());
    }

    public void getSecondLevelCacheHitCount() {
        System.out.println("SecondLevelCacheHitCount:   " + statistics.getSecondLevelCacheHitCount());
    }

    public void getSecondLevel() {
        SecondLevelCacheStatistics secondLevelCacheStatistics = statistics.getSecondLevelCacheStatistics("ru.otus.dobrovolsky.base.dataSets.UserDataSet");
        System.out.println("SecondLevelHitU:   " + secondLevelCacheStatistics.getHitCount());
        System.out.println("SecondLevelMissU:   " + secondLevelCacheStatistics.getMissCount());

        secondLevelCacheStatistics = statistics.getSecondLevelCacheStatistics("ru.otus.dobrovolsky.base.dataSets" +
                ".PhoneDataSet");
        System.out.println("SecondLevelHitP:   " + secondLevelCacheStatistics.getHitCount());
        System.out.println("SecondLevelMissP:   " + secondLevelCacheStatistics.getMissCount());

        secondLevelCacheStatistics = statistics.getSecondLevelCacheStatistics("ru.otus.dobrovolsky.base.dataSets" +
                ".AddressDataSet");
        System.out.println("SecondLevelHitA:   " + secondLevelCacheStatistics.getHitCount());
        System.out.println("SecondLevelMissA:   " + secondLevelCacheStatistics.getMissCount());
    }
}