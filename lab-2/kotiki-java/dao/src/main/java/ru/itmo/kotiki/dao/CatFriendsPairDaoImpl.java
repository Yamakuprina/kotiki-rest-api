package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.models.CatFriendsPair;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.kotiki.dao.utils.HibernateSessionFactory;

import java.util.List;

public class CatFriendsPairDaoImpl implements CatFriendsPairDao {
    @Override
    public CatFriendsPair findById(String id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(CatFriendsPair.class, id);
    }

    @Override
    public void save(CatFriendsPair catFriendsPair) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(catFriendsPair);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(CatFriendsPair catFriendsPair) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(catFriendsPair);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(CatFriendsPair catFriendsPair) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(catFriendsPair);
        tx1.commit();
        session.close();
    }

    @Override
    public List<CatFriendsPair> findAll() {
        List<CatFriendsPair> catFriendsPairs = (List<CatFriendsPair>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From CatFriendsPair").list();
        return catFriendsPairs;
    }
}
