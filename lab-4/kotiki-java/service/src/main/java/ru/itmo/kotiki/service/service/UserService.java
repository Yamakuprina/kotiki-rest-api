package ru.itmo.kotiki.service.service;

import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;

import java.util.List;

public interface UserService {

    List<CatDto> getAllCats();

    List<CatDto> getCatsWithCatColor(CatColor color);

    void saveCatFriendsPair(CatFriendsPairDto catFriendsPair);

    List<CatDto> getFriendsByCatId(String catId);

    void deleteCatFriendsPair(String catFriendsPairId);
}
