package ru.itmo.kotiki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.repository.CatRepository;
import ru.itmo.kotiki.dao.repository.OwnerRepository;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.OwnerDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatServiceImpl implements CatService {

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public List<CatDto> getAllCats() {
        return catsToCatDtos(catRepository.findAll());
    }

    @Override
    public CatDto findById(String id) {
        Cat cat = catRepository.findById(id).orElseThrow();
        return new CatDto(cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor());
    }

    @Override
    public void save(CatDto catDto) {
        Cat cat = new Cat(catDto.getName(), catDto.getBirthDate(), catDto.getBreed(), catDto.getColor());
        if (catDto.getOwner() != null) {
            cat.setOwner(catDto.getOwner());
        }
        if (catDto.getId() != null) {
            cat.setId(catDto.getId());
        }
        catRepository.save(cat);
    }

    @Override
    public void setOwnerById(String catId, String ownerId) {
        Cat cat = catRepository.findById(catId).orElseThrow();
        Owner owner = ownerRepository.findById(ownerId).orElseThrow();
        cat.setOwner(owner);
        catRepository.save(cat);
    }

    @Override
    public void delete(String id) {
        catRepository.deleteById(id);
    }

    @Override
    public OwnerDto findOwnerByCatId(String id) {
        return new OwnerDto(catRepository.findById(id).orElseThrow().getOwner().getName(), catRepository.findById(id).orElseThrow().getOwner().getBirthDate());
    }

    @Override
    public List<CatDto> getCatsWithCatColor(CatColor color) {
        List<Cat> allCats = catRepository.findAll();
        List<CatDto> colorCats = new ArrayList<>();
        for (Cat cat : allCats) {
            if (cat.getColor().equals(color)) {
                CatDto catDto = new CatDto(cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor());
                colorCats.add(catDto);
            }
        }
        return colorCats;
    }

    private List<CatDto> catsToCatDtos(List<Cat> cats) {
        ArrayList<CatDto> catDtos = new ArrayList<CatDto>();
        for (Cat cat : cats) {
            CatDto catDto = new CatDto(cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor());
            catDtos.add(catDto);
        }
        return catDtos;
    }
}
