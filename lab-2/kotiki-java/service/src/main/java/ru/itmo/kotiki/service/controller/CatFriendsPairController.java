package ru.itmo.kotiki.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;
import ru.itmo.kotiki.service.service.CatFriendsPairService;

@RestController
@RequestMapping("/friends/")
public class CatFriendsPairController {

    @Autowired
    private CatFriendsPairService catFriendsPairService;

    @PostMapping("save")
    public ResponseEntity save(@RequestBody CatFriendsPairDto catFriendsPairDto) {
        try {
            catFriendsPairService.save(catFriendsPairDto);
            return ResponseEntity.ok("Cat Friends Pair successfully saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id")
    public ResponseEntity ById(@RequestParam String catFriendsPairId) {
        try {
            return ResponseEntity.ok(catFriendsPairService.findById(catFriendsPairId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id/cat-friends")
    public ResponseEntity FriendsByCatId(@RequestParam String catId) {
        try {
            return ResponseEntity.ok(catFriendsPairService.getFriendsByCatId(catId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("id")
    public ResponseEntity delete(@RequestParam String catFriendsPairId) {
        try {
            catFriendsPairService.delete(catFriendsPairId);
            return ResponseEntity.ok("Cat successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("all")
    public ResponseEntity AllCatFriendsPairs() {
        try {
            return ResponseEntity.ok(catFriendsPairService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
