package ru.itmo.kotiki.service.service;

import ru.itmo.kotiki.service.dto.UserDto;

import java.util.List;

public interface UserStorageService {
    void saveUser(UserDto userDto);

    void deleteUser(String userId);

    List<UserDto> getAllUsers();

    void setOwnerToUser(String userId, String ownerId);
}
