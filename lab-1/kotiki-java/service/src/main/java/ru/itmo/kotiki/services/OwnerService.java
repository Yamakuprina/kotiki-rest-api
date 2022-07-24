package ru.itmo.kotiki.services;

import ru.itmo.kotiki.dao.OwnerDao;
import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;

import java.util.List;

public class OwnerService {
    private OwnerDao ownerDao;

    public OwnerService(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    public Owner findById(String id){
        return ownerDao.findById(id);
    }

    public void save(Owner owner){
        ownerDao.save(owner);
    }

    public void update(Owner owner){
        ownerDao.update(owner);
    }

    public void delete(Owner owner){
        ownerDao.delete(owner);
    }

    public List<Owner> findAll(){
        return ownerDao.findAll();
    }

    public List<Cat> findOwnerCatsById(String ownerId){
        return ownerDao.findOwnerCatsById(ownerId);
    }
}
