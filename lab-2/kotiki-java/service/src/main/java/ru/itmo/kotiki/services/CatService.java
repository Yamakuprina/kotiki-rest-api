package ru.itmo.kotiki.services;

import ru.itmo.kotiki.dao.CatDao;
import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;

import java.util.List;

public class CatService {
    private CatDao catDao;

    public CatService(CatDao catDao) {
        this.catDao = catDao;
    }

    public Cat findById(String id){
        return catDao.findById(id);
    }

    public void save(Cat cat){
        catDao.save(cat);
    }

    public void update(Cat cat){
        catDao.update(cat);
    }

    public void delete(Cat cat){
        catDao.delete(cat);
    }

    public List<Cat> findAll(){
        return catDao.findAll();
    }

    public List<Cat> findAllFriends(String id){
        return catDao.findAllFriends(id);
    }

    public Owner findOwnerByCatId(String id){
        return catDao.findOwnerByCatId(id);
    }
}
