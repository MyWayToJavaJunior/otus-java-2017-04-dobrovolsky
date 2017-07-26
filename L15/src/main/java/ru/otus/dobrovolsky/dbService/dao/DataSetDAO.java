package ru.otus.dobrovolsky.dbService.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.dobrovolsky.dataSet.AddressDataSet;
import ru.otus.dobrovolsky.dataSet.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DataSetDAO {
    private Session session;

    public DataSetDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return (UserDataSet) session.createQuery(
                "select u " +
                        "from ru.otus.dobrovolsky.dataSet.UserDataSet u " +
                        "where u.id = :id ")
                .setParameter("id", id)
                .setCacheable(true)
                .setCacheRegion("userById")
                .getSingleResult();
    }

    public AddressDataSet readAddressById(long id) {
        return (AddressDataSet) session.createQuery(
                "select a " +
                        "from ru.otus.dobrovolsky.dataSet.AddressDataSet a " +
                        "where user_id = :id ")
                .setParameter("id", id)
                .setCacheable(true)
                .setCacheRegion("ru.otus.dobrovolsky.dataSet.AddressDataSet")
                .getSingleResult();
    }

    public UserDataSet readByNamedQuery(long id) {
        return (UserDataSet) session.createNamedQuery("namedQuery").setParameter("id", id).setCacheable(true).setCacheRegion("namedQuery").uniqueResult();
    }

    public UserDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UserDataSet> query = session.createQuery(criteria).setCacheable(true).setCacheRegion("ru.otus" +
                ".dobrovolsky.base.dataSet.UserDataSet");
        return query.uniqueResult();
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).setCacheable(true).setCacheRegion("ru.otus.dobrovolsky.dataSet.UserDataSet").list();
    }
}