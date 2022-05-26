import ru.itmo.kotiki.models.CatColor;
import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import ru.itmo.kotiki.dao.CatDao;
import ru.itmo.kotiki.dao.CatDaoImpl;
import ru.itmo.kotiki.dao.OwnerDao;
import ru.itmo.kotiki.dao.OwnerDaoImpl;
import ru.itmo.kotiki.services.CatService;
import ru.itmo.kotiki.services.OwnerService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

public class Tests {
    @Mock
    CatDao catDao = new CatDaoImpl();

    @Mock
    OwnerDao ownerDao = new OwnerDaoImpl();

    CatService catService;
    OwnerService ownerService;
    List<Cat> allCats;
    Owner owner;

    MockitoSession session;

    @BeforeEach
    public void beforeMethod() {
        session = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
        catService = new CatService(catDao);
        ownerService = new OwnerService(ownerDao);
        owner = new Owner("Anton", new Date(12346578));
        allCats = Stream.of(new Cat("Barsik", new Date(12346578), "Boba", CatColor.BLACK, owner), new Cat("Karasik", new Date(12346578), "Biba", CatColor.GINGER, owner)).toList();
    }

    @AfterEach
    public void afterMethod() {
        session.finishMocking();
    }

    @Test
    public void TestCatsFindAll() {
        Mockito.when(catDao.findAll()).thenReturn(allCats);
        List<Cat> cats = catService.findAll();
        Assertions.assertNotNull(cats);
    }

    @Test
    public void TestCatsFindAllFriends() {
        Mockito.when(catDao.findAll()).thenReturn(allCats);
        Mockito.when(catDao.findAllFriends(allCats.get(0).getId())).thenReturn(Stream.of(allCats.get(1)).toList());
        List<Cat> cats = catService.findAll();
        Cat cat = cats.get(0);
        List<Cat> catFriends = catService.findAllFriends(cat.getId());
        Assertions.assertEquals(catFriends.get(0), cats.get(1));
    }

    @Test
    public void TestFindOwnerByCatId(){
        Mockito.when(catDao.findAll()).thenReturn(allCats);
        Mockito.when(catDao.findOwnerByCatId(allCats.get(0).getId())).thenReturn(owner);
        List<Cat> cats = catService.findAll();
        Cat cat = cats.get(0);
        Owner catOwner = catService.findOwnerByCatId(cat.getId());
        Assertions.assertEquals(catOwner, owner);
    }

    @Test
    public void TestFindOwnerCatsById(){
        Mockito.when(ownerDao.findOwnerCatsById(owner.getId())).thenReturn(allCats);
        List<Cat> cats = ownerService.findOwnerCatsById(owner.getId());
        Assertions.assertEquals(cats, allCats);
    }
}
