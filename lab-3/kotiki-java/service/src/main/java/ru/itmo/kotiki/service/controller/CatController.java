package ru.itmo.kotiki.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.service.CatService;

@RestController
@RequestMapping("/cats/")
public class CatController {

    @Autowired
    private CatService catService;

    @GetMapping("all")
    public ResponseEntity AllCats() {
        try {
            return ResponseEntity.ok(catService.getAllCats());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id")
    public ResponseEntity ById(@RequestParam String id) {
        try {
            return ResponseEntity.ok(catService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id/owner")
    public ResponseEntity OwnerByCatId(@RequestParam String id) {
        try {
            return ResponseEntity.ok(catService.findOwnerByCatId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("color")
    public ResponseEntity CatsWithCatColor(@RequestParam CatColor color) {
        try {
            return ResponseEntity.ok(catService.getCatsWithCatColor(color));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("save")
    public ResponseEntity save(@RequestBody CatDto catDto) {
        try {
            catService.save(catDto);
            return ResponseEntity.ok("Cat successfully saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("id/owner")
    public ResponseEntity setOwnerById(@RequestParam String catId, @RequestParam String ownerId) {
        try {
            catService.setOwnerById(catId, ownerId);
            return ResponseEntity.ok("Successfully set new owner.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("id")
    public ResponseEntity delete(@RequestParam String id) {
        try {
            catService.delete(id);
            return ResponseEntity.ok("Cat successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
