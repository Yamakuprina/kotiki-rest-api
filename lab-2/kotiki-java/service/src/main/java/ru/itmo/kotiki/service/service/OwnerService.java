package ru.itmo.kotiki.service.service;

import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.OwnerDto;

import java.util.List;

public interface OwnerService {
    OwnerDto findById(String id);

    void save(OwnerDto ownerDto);

    void delete(String id);

    List<OwnerDto> getAllOwners();

    List<CatDto> getCatsByOwnerId(String ownerId);
}
