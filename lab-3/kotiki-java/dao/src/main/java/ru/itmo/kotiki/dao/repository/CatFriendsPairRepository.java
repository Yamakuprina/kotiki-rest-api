package ru.itmo.kotiki.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dao.entity.CatFriendsPair;

@Repository
public interface CatFriendsPairRepository extends JpaRepository<CatFriendsPair, String> {
}
