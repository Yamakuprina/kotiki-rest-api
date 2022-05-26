package ru.itmo.kotiki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.CatFriendsPair;
import ru.itmo.kotiki.dao.repository.CatFriendsPairRepository;
import ru.itmo.kotiki.dao.repository.CatRepository;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatFriendsPairServiceImpl implements CatFriendsPairService {

    @Autowired
    private CatFriendsPairRepository catFriendsPairRepository;

    @Autowired
    private CatRepository catRepository;

    @Override
    public CatFriendsPairDto findById(String id) {
        return new CatFriendsPairDto(catFriendsPairRepository.findById(id).orElseThrow().getCat1Id(), catFriendsPairRepository.findById(id).orElseThrow().getCat2Id());
    }

    @Override
    public void save(CatFriendsPairDto catFriendsPairDto) {
        CatFriendsPair catFriendsPair = new CatFriendsPair(catFriendsPairDto.getCat1Id(), catFriendsPairDto.getCat2Id());
        if (catFriendsPairDto.getId() != null) {
            catFriendsPair.setId(catFriendsPairDto.getId());
        }
        catFriendsPairRepository.save(catFriendsPair);
    }

    @Override
    public void delete(String catFriendsPairId) {
        catFriendsPairRepository.deleteById(catFriendsPairId);
    }

    @Override
    public List<CatFriendsPairDto> getAll() {
        return catFriendsPairToCatFriendsPairDtos(catFriendsPairRepository.findAll());
    }

    @Override
    public List<CatDto> getFriendsByCatId(String catId) {
        List<CatFriendsPair> catFriendsPairs = catFriendsPairRepository.findAll();
        List<String> catFriendsIds = new ArrayList<>();
        for (CatFriendsPair catFriendsPair : catFriendsPairs) {
            if (catFriendsPair.getCat1Id().equals(catId)) {
                catFriendsIds.add(catFriendsPair.getCat2Id());
            }
            if (catFriendsPair.getCat2Id().equals(catId)) {
                catFriendsIds.add(catFriendsPair.getCat1Id());
            }
        }
        List<Cat> catFriends = catRepository.findAllById(catFriendsIds);
        return catsToCatDtos(catFriends);
    }

    private List<CatFriendsPairDto> catFriendsPairToCatFriendsPairDtos(List<CatFriendsPair> catFriendsPairs) {
        ArrayList<CatFriendsPairDto> catFriendsPairDtos = new ArrayList<CatFriendsPairDto>();
        for (CatFriendsPair catFriendsPair : catFriendsPairs) {
            CatFriendsPairDto catFriendsPairDto = new CatFriendsPairDto(catFriendsPair.getCat1Id(), catFriendsPair.getCat2Id());
            catFriendsPairDtos.add(catFriendsPairDto);
        }
        return catFriendsPairDtos;
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
