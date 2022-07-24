package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;

import java.util.List;

public interface CatDao {

    Cat findById(String id);

    void save(Cat cat);

    void update(Cat cat);

    void delete(Cat cat);

    List<Cat> findAll();

    List<Cat> findAllFriends(String id);

    Owner findOwnerByCatId(String id);
}
