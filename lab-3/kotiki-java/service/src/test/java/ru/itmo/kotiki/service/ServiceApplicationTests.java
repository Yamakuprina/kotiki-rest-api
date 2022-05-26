package ru.itmo.kotiki.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.dao.entity.CatFriendsPair;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.repository.CatFriendsPairRepository;
import ru.itmo.kotiki.dao.repository.CatRepository;
import ru.itmo.kotiki.dao.repository.OwnerRepository;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;
import ru.itmo.kotiki.service.dto.OwnerDto;
import ru.itmo.kotiki.service.service.CatFriendsPairServiceImpl;
import ru.itmo.kotiki.service.service.CatServiceImpl;
import ru.itmo.kotiki.service.service.OwnerServiceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ServiceApplicationTests {

    @Mock
    private CatRepository catRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CatFriendsPairRepository catFriendsPairRepository;

    @InjectMocks
    private CatServiceImpl catService;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @InjectMocks
    private CatFriendsPairServiceImpl catFriendsPairService;

    @BeforeEach
    public void beforeMethod() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCat() {
        Cat cat = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        String catId = cat.getId();
        CatDto catDto = new CatDto(cat);
        catService.save(catDto);
        when(catRepository.findById(catId)).thenReturn(Optional.of(cat));
        Assertions.assertEquals(catService.findById(catId).getName(), "Pushok");
    }

    @Test
    void findAllCats() {
        Cat cat1 = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Cat cat2 = new Cat("Banana", Date.valueOf("2090-12-11"), "Biba", CatColor.GINGER);
        catService.save(new CatDto(cat1));
        catService.save(new CatDto(cat2));
        when(catRepository.findAll()).thenReturn(List.of(cat1, cat2));
        Assertions.assertEquals(catService.getAllCats().size(), 2);
    }

    @Test
    void deleteCat() {
        Cat cat1 = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Cat cat2 = new Cat("Banana", Date.valueOf("2090-12-11"), "Biba", CatColor.GINGER);
        catService.save(new CatDto(cat1));
        catService.save(new CatDto(cat2));
        catService.delete(cat1.getId());
        when(catRepository.findAll()).thenReturn(List.of(cat2));
        Assertions.assertEquals(catService.getAllCats().size(), 1);
    }

    @Test
    void setOwnerByCatId() {
        Cat cat = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Owner owner = new Owner("Putin", Date.valueOf("2001-7-7"));
        catService.save(new CatDto(cat));
        ownerService.save(new OwnerDto(owner));
        when(catRepository.findById(cat.getId())).thenReturn(Optional.of(cat));
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        catService.setOwnerById(cat.getId(), owner.getId());
        cat.setOwner(owner);
        Assertions.assertEquals(catService.findById(cat.getId()).getOwner().getId(), owner.getId());
    }

    @Test
    void findOwnerByCatId() {
        Cat cat = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        String catId = cat.getId();
        Owner owner = new Owner("Putin", Date.valueOf("2001-7-7"));
        cat.setOwner(owner);
        CatDto catDto = new CatDto(cat);
        OwnerDto ownerDto = new OwnerDto(owner);
        ownerService.save(ownerDto);
        catService.save(catDto);
        when(catRepository.findById(catId)).thenReturn(Optional.of(cat));
        Assertions.assertEquals(catService.findOwnerByCatId(catId).getId(), owner.getId());
    }

    @Test
    void getCatsWithCatColor() {
        Cat cat1 = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Cat cat2 = new Cat("Banana", Date.valueOf("2090-12-11"), "Biba", CatColor.GINGER);
        catService.save(new CatDto(cat1));
        catService.save(new CatDto(cat2));
        when(catRepository.findAll()).thenReturn(List.of(cat1, cat2));
        Assertions.assertTrue(catService.getCatsWithCatColor(CatColor.BLACK).isEmpty());
    }

    @Test
    void getCatsWithOwnerId() {
        Cat cat1 = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Cat cat2 = new Cat("Banana", Date.valueOf("2090-12-11"), "Biba", CatColor.GINGER);
        Owner owner = new Owner("Putin", Date.valueOf("2001-7-7"));
        cat1.setOwner(owner);
        cat2.setOwner(owner);
        catService.save(new CatDto(cat1));
        catService.save(new CatDto(cat2));
        ownerService.save(new OwnerDto(owner));
        when(catRepository.findAll()).thenReturn(List.of(cat1, cat2));
        Assertions.assertEquals(ownerService.getCatsByOwnerId(owner.getId()).size(), 2);
    }

    @Test
    void getFriendsWithCatId() {
        Cat cat1 = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Cat cat2 = new Cat("Banana", Date.valueOf("2090-12-11"), "Biba", CatColor.GINGER);
        CatFriendsPair catFriendsPair = new CatFriendsPair(cat1.getId(), cat2.getId());
        catService.save(new CatDto(cat1));
        catService.save(new CatDto(cat2));
        catFriendsPairService.save(new CatFriendsPairDto(catFriendsPair));
        when(catFriendsPairRepository.findAll()).thenReturn(List.of(catFriendsPair));
        when(catRepository.findAllById(List.of(cat2.getId()))).thenReturn(List.of(cat2));
        Assertions.assertEquals(catFriendsPairService.getFriendsByCatId(cat1.getId()).get(0).getId(), cat2.getId());
    }

}
