package ru.itmo.kotiki.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table (name = "cats")
public class Cat {
    @Column (name = "name")
    private String name;
    @Id
    private String id;
    @Column (name = "birth_date")
    private Date birthDate;
    @Column (name = "breed")
    private String breed;
    @Column (name = "color")
    private CatColor color;
    @ManyToOne
    @JoinColumn (name = "owner_id")
    private Owner owner;

    public Cat(String name, Date birthDate, String breed, CatColor color, Owner owner) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
    }

    public Cat() {
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner){
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
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
