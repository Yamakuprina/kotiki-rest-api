package ru.itmo.kotiki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.dao.entity.CatFriendsPair;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.repository.CatFriendsPairRepository;
import ru.itmo.kotiki.dao.repository.CatRepository;
import ru.itmo.kotiki.dao.repository.OwnerRepository;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;
import ru.itmo.kotiki.service.userDetails.KotikiUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatFriendsPairRepository catFriendsPairRepository;

    @Override
    public List<CatDto> getAllCats() {
        Owner userOwner = ((KotikiUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getOwner();
        List<Cat> allCats = catRepository.findByOwner(userOwner);
        return catsToCatDtos(allCats);
    }

    @Override
    public List<CatDto> getCatsWithCatColor(CatColor color) {
        Owner userOwner = ((KotikiUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getOwner();
        List<Cat> allCats = catRepository.findByColorAndOwner(color, userOwner);
        return catsToCatDtos(allCats);
    }

    @Override
    public void saveCatFriendsPair(CatFriendsPairDto catFriendsPairDto) {
        CatFriendsPair catFriendsPair = new CatFriendsPair(catFriendsPairDto.getCat1Id(), catFriendsPairDto.getCat2Id());
        if (catFriendsPairDto.getId() != null) {
            catFriendsPair.setId(catFriendsPairDto.getId());
        }
        catFriendsPairRepository.save(catFriendsPair);
    }

    @Override
    public List<CatDto> getFriendsByCatId(String catId) {
        List<CatFriendsPair> catPairs = catFriendsPairRepository.findByCat1IdOrCat2Id(catId, catId);
        List<String> catFriendsIds = catFriendsPairsToIds(catPairs, catId);
        List<Cat> catFriends = catRepository.findAllById(catFriendsIds);
        return catsToCatDtos(catFriends);
    }

    @Override
    public void deleteCatFriendsPair(String catFriendsPairId) {
        catFriendsPairRepository.deleteById(catFriendsPairId);
    }

    private List<CatDto> catsToCatDtos(List<Cat> cats) {
        ArrayList<CatDto> catDtos = new ArrayList<CatDto>();
        for (Cat cat : cats) {
            CatDto catDto = new CatDto(cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor());
            catDto.setId(cat.getId());
            catDtos.add(catDto);
        }
        return catDtos;
    }

    private List<String> catFriendsPairsToIds(List<CatFriendsPair> pairs, String id){
        List<String> ids = new ArrayList<>();
        for (CatFriendsPair catFriendsPair : pairs) {
            if (catFriendsPair.getCat1Id().equals(id)) {
                ids.add(catFriendsPair.getCat2Id());
            }
            if (catFriendsPair.getCat2Id().equals(id)) {
                ids.add(catFriendsPair.getCat1Id());
            }
        }
        return ids;
    }
}
