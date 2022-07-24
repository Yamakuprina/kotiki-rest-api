package ru.itmo.kotiki.service.service;

import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.OwnerDto;

import java.util.List;

public interface CatService {
    List<CatDto> getAllCats();

    CatDto findById(String id);

    void save(CatDto cat);

    void setOwnerById(String catId, String ownerId);

    void delete(String id);

    OwnerDto findOwnerByCatId(String id);

    List<CatDto> getCatsWithCatColor(CatColor color);
}
