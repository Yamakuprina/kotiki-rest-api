package ru.itmo.kotiki.service.dto;

import ru.itmo.kotiki.dao.entity.CatFriendsPair;

public class CatFriendsPairDto {

    private String id;

    private final String cat1Id;

    private final String cat2Id;

    public CatFriendsPairDto(String cat1Id, String cat2Id) {
        this.cat1Id = cat1Id;
        this.cat2Id = cat2Id;
    }

    public CatFriendsPairDto(CatFriendsPair catFriendsPair) {
        this.cat1Id = catFriendsPair.getCat1Id();
        this.cat2Id = catFriendsPair.getCat2Id();
        this.id = catFriendsPair.getId();
    }

    public String getId() {
        return id;
    }

    public String getCat1Id() {
        return cat1Id;
    }

    public String getCat2Id() {
        return cat2Id;
    }
}
