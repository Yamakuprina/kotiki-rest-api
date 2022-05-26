package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.models.CatFriendsPair;

import java.util.List;

public interface CatFriendsPairDao {
    CatFriendsPair findById(String id);

    void save(CatFriendsPair catFriendsPair);

    void update(CatFriendsPair catFriendsPair);

    void delete(CatFriendsPair catFriendsPair);

    List<CatFriendsPair> findAll();
}
