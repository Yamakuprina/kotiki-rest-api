package ru.itmo.kotiki.service.dto;

import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.dao.entity.Owner;

import java.sql.Date;

public class CatDto {

    private final String name;

    private final Date birthDate;

    private final String breed;

    private String id;

    private final CatColor color;

    private Owner owner;

    public CatDto(String name, Date birthDate, String breed, CatColor color) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
    }

    public CatDto(Cat cat) {
        id = cat.getId();
        name = cat.getName();
        birthDate = cat.getBirthDate();
        breed = cat.getBreed();
        color = cat.getColor();
        owner = cat.getOwner();
    }

    public Owner getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public CatColor getColor() {
        return color;
    }
}
