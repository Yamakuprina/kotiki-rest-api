package ru.itmo.kotiki.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table (name = "cat_friends")
public class CatFriendsPair {
    @Id
    private String id;
    @Column (name = "cat1_id")
    private String cat1Id;
    @Column (name = "cat2_id")
    private String cat2Id;

    public CatFriendsPair(String cat1_id, String cat2_id) {
        this.cat1Id = cat1_id;
        this.cat2Id = cat2_id;
        this.id = UUID.randomUUID().toString();
    }

    public CatFriendsPair() {
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
