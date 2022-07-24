package ru.itmo.kotiki.service.dto;

import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Owner;

import java.sql.Date;
import java.util.List;

public class OwnerDto {
    private final String name;

    private final Date birthDate;

    private List<Cat> cats;

    private String id;

    public OwnerDto(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public OwnerDto(Owner owner) {
        name = owner.getName();
        birthDate = owner.getBirthDate();
        cats = owner.getCats();
        id = owner.getId();
    }

    public String getId() {
        return id;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}
