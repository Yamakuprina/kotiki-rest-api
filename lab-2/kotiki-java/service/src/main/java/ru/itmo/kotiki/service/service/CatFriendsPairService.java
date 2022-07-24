package ru.itmo.kotiki.service.service;

import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;

import java.util.List;

public interface CatFriendsPairService {
    CatFriendsPairDto findById(String id);

    void save(CatFriendsPairDto catFriendsPair);

    void delete(String catFriendsPairId);

    List<CatFriendsPairDto> getAll();

    List<CatDto> getFriendsByCatId(String catId);
}
