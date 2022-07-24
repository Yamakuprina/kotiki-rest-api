package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.kotiki.dao.utils.HibernateSessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CatDaoImpl implements CatDao {
    @Override
    public Cat findById(String id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Cat.class, id);
    }

    @Override
    public void save(Cat cat) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(cat);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Cat cat) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(cat);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Cat cat) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(cat);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Cat> findAll() {
        List<Cat> cats = (List<Cat>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Cat").list();
        return cats;
    }

    @Override
    public List<Cat> findAllFriends(String id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String hql = "SELECT c.cat2Id FROM CatFriendsPair c WHERE c.cat1Id=:id";
        Query query = session.createQuery(hql, String.class);
        query.setParameter("id", id);
        List<String> catFriends_id = query.getResultList();
        hql = "SELECT c.cat1Id FROM CatFriendsPair c WHERE c.cat2Id=:id";
        query = session.createQuery(hql, String.class);
        query.setParameter("id", id);
        catFriends_id.addAll(query.getResultList());
        List<Cat> catFriends = new ArrayList<>();
        for (String cat_id : catFriends_id) {
            catFriends.add(findById(cat_id));
        }
        return catFriends;
    }

    @Override
    public Owner findOwnerByCatId(String id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String hql = "SELECT cat.owner FROM Cat cat WHERE cat.id=:cat_id";
        Query query = session.createQuery(hql, Owner.class);
        query.setParameter("cat_id", id);
        Owner owner = (Owner) query.getSingleResult();
        return owner;
    }
}
