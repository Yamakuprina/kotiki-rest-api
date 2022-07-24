package ru.itmo.kotiki.service.dto;

import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.entity.User;

public class UserDto {

    private final String id;

    private final String username;

    private final String password;

    private final String role;

    private final boolean enabled;

    private Owner owner;

    public UserDto(String id, String username, String password, String role, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
        this.owner = user.getOwner();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }
}
