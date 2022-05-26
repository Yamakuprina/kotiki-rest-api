package ru.itmo.kotiki.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.service.dto.CatFriendsPairDto;
import ru.itmo.kotiki.service.service.CatFriendsPairService;
import ru.itmo.kotiki.service.service.UserService;
import ru.itmo.kotiki.service.userDetails.UserRole;

import java.util.Collection;

@RestController
@RequestMapping("/friends/")
public class CatFriendsPairController {

    @Autowired
    private CatFriendsPairService catFriendsPairService;

    @Autowired
    private UserService userService;

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody CatFriendsPairDto catFriendsPairDto) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                catFriendsPairService.save(catFriendsPairDto);
            } else {
                userService.saveCatFriendsPair(catFriendsPairDto);
            }
            return ResponseEntity.ok("Cat Friends Pair successfully saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id")
    public ResponseEntity<Object> ById(@RequestParam String catFriendsPairId) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(catFriendsPairService.findById(catFriendsPairId));
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id/cat-friends")
    public ResponseEntity<Object> FriendsByCatId(@RequestParam String catId) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(catFriendsPairService.getFriendsByCatId(catId));
            } else {
                return ResponseEntity.ok(userService.getFriendsByCatId(catId));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("id")
    public ResponseEntity<Object> delete(@RequestParam String catFriendsPairId) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                catFriendsPairService.delete(catFriendsPairId);
            } else {
                userService.deleteCatFriendsPair(catFriendsPairId);
            }
            return ResponseEntity.ok("Cat-Friends pair successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("all")
    public ResponseEntity<Object> AllCatFriendsPairs() {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(catFriendsPairService.getAll());
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
