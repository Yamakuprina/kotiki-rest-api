package ru.itmo.kotiki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.repository.CatRepository;
import ru.itmo.kotiki.dao.repository.OwnerRepository;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.OwnerDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CatRepository catRepository;

    @Override
    public OwnerDto findById(String id) {
        return new OwnerDto(ownerRepository.findById(id).orElseThrow().getName(), ownerRepository.findById(id).orElseThrow().getBirthDate());
    }

    @Override
    public void save(OwnerDto ownerDto) {
        Owner owner = new Owner(ownerDto.getName(), ownerDto.getBirthDate());
        if (ownerDto.getCats() != null) {
            owner.setCats(ownerDto.getCats());
        }
        if (ownerDto.getId() != null) {
            owner.setId(ownerDto.getId());
        }
        ownerRepository.save(owner);
    }

    @Override
    public void delete(String id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        return ownersToOwnerDtos(ownerRepository.findAll());
    }

    @Override
    public List<CatDto> getCatsByOwnerId(String ownerId) {
        List<Cat> allCats = catRepository.findAll();
        List<Cat> ownerCats = new ArrayList<>();
        for (Cat cat : allCats) {
            if (cat.getOwner().getId().equals(ownerId)) {
                ownerCats.add(cat);
            }
        }
        return catsToCatDtos(ownerCats);
    }

    private List<CatDto> catsToCatDtos(List<Cat> cats) {
        ArrayList<CatDto> catDtos = new ArrayList<CatDto>();
        for (Cat cat : cats) {
            CatDto catDto = new CatDto(cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor());
            catDtos.add(catDto);
        }
        return catDtos;
    }

    private List<OwnerDto> ownersToOwnerDtos(List<Owner> owners) {
        ArrayList<OwnerDto> ownerDtos = new ArrayList<OwnerDto>();
        for (Owner owner : owners) {
            OwnerDto ownerDto = new OwnerDto(owner.getName(), owner.getBirthDate());
            ownerDtos.add(ownerDto);
        }
        return ownerDtos;
    }
}
