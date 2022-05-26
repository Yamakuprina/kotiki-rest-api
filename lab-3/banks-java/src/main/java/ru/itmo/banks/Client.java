package ru.itmo.banks;

import java.util.UUID;

public class Client {
    private final String id;
    public final String name;
    public final String surname;
    private String address = "";
    private String passport = "";
    public Client(String name, String surname) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
    }

    public String getNameSurname() {
        return name + " " + surname;
    }

    public Boolean verified() {
        return !address.isBlank() && !passport.isBlank();
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void getUpdate(String message) {
        System.out.println(message);
    }
}
