package ru.itmo.kotiki.services;

import ru.itmo.kotiki.dao.CatFriendsPairDao;
import ru.itmo.kotiki.models.CatFriendsPair;

import java.util.List;

public class CatFriendsPairService {
    private CatFriendsPairDao catFriendsPairDao;

    public CatFriendsPairService(CatFriendsPairDao catFriendsPairDao) {
        this.catFriendsPairDao = catFriendsPairDao;
    }

    public CatFriendsPair findById(String id){
        return catFriendsPairDao.findById(id);
    }

    public void save(CatFriendsPair catFriendsPair){
        catFriendsPairDao.save(catFriendsPair);
    }

    public void update(CatFriendsPair catFriendsPair){
        catFriendsPairDao.update(catFriendsPair);
    }

    public void delete(CatFriendsPair catFriendsPair){
        catFriendsPairDao.delete(catFriendsPair);
    }

    public List<CatFriendsPair> findAll(){
        return catFriendsPairDao.findAll();
    }
}
