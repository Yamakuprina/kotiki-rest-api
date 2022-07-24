package ru.itmo.kotiki.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dao.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {
}
