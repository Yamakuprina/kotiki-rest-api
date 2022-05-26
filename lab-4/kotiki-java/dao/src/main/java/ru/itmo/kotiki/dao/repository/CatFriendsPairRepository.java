package ru.itmo.kotiki.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dao.entity.CatFriendsPair;

import java.util.List;

@Repository
public interface CatFriendsPairRepository extends JpaRepository<CatFriendsPair, String> {
    List<CatFriendsPair> findByCat1IdOrCat2Id(String Cat1Id, String Cat2Id);
}
