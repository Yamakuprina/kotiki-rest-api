package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;

import java.util.List;

public interface OwnerDao {
    Owner findById(String id);

    void save(Owner owner);

    void update(Owner owner);

    void delete(Owner owner);

    List<Owner> findAll();

    List<Cat> findOwnerCatsById(String ownerId);
}
