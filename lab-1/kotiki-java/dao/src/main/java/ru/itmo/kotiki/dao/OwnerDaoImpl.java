package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.kotiki.dao.utils.HibernateSessionFactory;

import javax.persistence.Query;
import java.util.List;

public class OwnerDaoImpl implements OwnerDao {
    @Override
    public Owner findById(String id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Owner.class, id);
    }

    @Override
    public void save(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(owner);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(owner);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(owner);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = (List<Owner>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Owner").list();
        return owners;
    }

    @Override
    public List<Cat> findOwnerCatsById(String ownerId) {

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String hql = "SELECT cat FROM Cat cat WHERE cat.owner.id=:owner_id";
        Query query = session.createQuery(hql, Cat.class);
        query.setParameter("owner_id", ownerId);
        List<Cat> ownerCats = query.getResultList();
        return ownerCats;
    }
}
