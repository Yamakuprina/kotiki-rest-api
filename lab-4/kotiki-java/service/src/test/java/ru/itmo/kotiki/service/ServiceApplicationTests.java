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
import org.springframework.security.test.context.support.WithMockUser;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.dao.entity.CatFriendsPair;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.repository.CatFriendsPairRepository;
import ru.itmo.kotiki.dao.repository.CatRepository;
import ru.itmo.kotiki.dao.repository.OwnerRepository;
import ru.itmo.kotiki.dao.repository.UserRepository;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;
import ru.itmo.kotiki.service.dto.OwnerDto;
import ru.itmo.kotiki.service.service.CatFriendsPairServiceImpl;
import ru.itmo.kotiki.service.service.CatServiceImpl;
import ru.itmo.kotiki.service.service.OwnerServiceImpl;
import ru.itmo.kotiki.service.service.UserDetailsServiceImpl;

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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CatServiceImpl catService;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @InjectMocks
    private CatFriendsPairServiceImpl catFriendsPairService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void beforeMethod() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveCat() {
        Cat cat = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        String catId = cat.getId();
        CatDto catDto = new CatDto(cat);
        catService.save(catDto);
        when(catRepository.findById(catId)).thenReturn(Optional.of(cat));
        Assertions.assertEquals(catService.findById(catId).getName(), "Pushok");
    }

    @Test
    @WithMockUser(roles = "USER")
    void findAllCats() {
        Cat cat1 = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Cat cat2 = new Cat("Banana", Date.valueOf("2090-12-11"), "Biba", CatColor.GINGER);
        catService.save(new CatDto(cat1));
        catService.save(new CatDto(cat2));
        when(catRepository.findAll()).thenReturn(List.of(cat1, cat2));
        Assertions.assertEquals(catService.getAllCats().size(), 2);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "USER")
    void getCatsWithCatColor() {
        Cat cat1 = new Cat("Pushok", Date.valueOf("2010-11-12"), "Boba", CatColor.GINGER);
        Cat cat2 = new Cat("Banana", Date.valueOf("2090-12-11"), "Biba", CatColor.GINGER);
        catService.save(new CatDto(cat1));
        catService.save(new CatDto(cat2));
        when(catRepository.findAll()).thenReturn(List.of(cat1, cat2));
        Assertions.assertTrue(catService.getCatsWithCatColor(CatColor.BLACK).isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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

}
