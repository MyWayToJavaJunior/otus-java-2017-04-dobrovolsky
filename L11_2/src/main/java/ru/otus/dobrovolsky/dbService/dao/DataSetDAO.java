package ru.otus.dobrovolsky.dbService.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.dobrovolsky.base.dataSets.DataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DataSetDAO {
    private Session session;

    public DataSetDAO(Session session) {
        this.session = session;
    }

    public <T extends DataSet> void save(T dataSet) {
        session.save(dataSet);
    }

    public <T extends DataSet> T read(Class<T> clazz, long id) {
        return session.load(clazz, id);
    }

    public <T extends DataSet> T readByName(Class<T> clazz, String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> from = criteria.from(clazz);
        criteria.where(builder.equal(from.get("name"), name));
        Query<T> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).list();
    }
}