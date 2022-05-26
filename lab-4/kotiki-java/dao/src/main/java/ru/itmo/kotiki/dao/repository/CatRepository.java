package ru.itmo.kotiki.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.dao.entity.Owner;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, String> {

    List<Cat> findByColor(CatColor color);
    List<Cat> findByColorAndOwner(CatColor color, Owner owner);
    List<Cat> findByOwner(Owner owner);
}
