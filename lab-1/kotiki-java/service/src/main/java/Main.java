import ru.itmo.kotiki.models.Cat;
import ru.itmo.kotiki.models.Owner;
import ru.itmo.kotiki.dao.CatDaoImpl;
import ru.itmo.kotiki.services.CatService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CatService catService = new CatService(new CatDaoImpl());
        List<Cat> cats = catService.findAll();
        for (Cat cat : cats) {
            System.out.println(cat.getId());
        }
        Owner owner = catService.findOwnerByCatId("04318c2a-0c2d-47b0-803e-4bf7463934d8");
        System.out.println(owner.getId());
    }
}
