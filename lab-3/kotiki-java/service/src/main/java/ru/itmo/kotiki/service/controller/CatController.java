package ru.itmo.kotiki.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.dao.entity.CatColor;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.service.CatService;
import ru.itmo.kotiki.service.service.UserService;
import ru.itmo.kotiki.service.userDetails.UserRole;

import java.util.Collection;

@RestController
@RequestMapping("/cats/")
public class CatController {

    @Autowired
    private CatService catService;

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public ResponseEntity<Object> AllCats() {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(catService.getAllCats());
            } else {
                return ResponseEntity.ok(userService.getAllCats());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id")
    public ResponseEntity<Object> ById(@RequestParam String id) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(catService.findById(id));
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id/owner")
    public ResponseEntity<Object> OwnerByCatId(@RequestParam String id) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(catService.findOwnerByCatId(id));
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("color")
    public ResponseEntity<Object> CatsWithCatColor(@RequestParam CatColor color) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(catService.getCatsWithCatColor(color));
            } else {
                return ResponseEntity.ok(userService.getCatsWithCatColor(color));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody CatDto catDto) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                catService.save(catDto);
                return ResponseEntity.ok("Cat successfully saved.");
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("id/owner")
    public ResponseEntity<Object> setOwnerById(@RequestParam String catId, @RequestParam String ownerId) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                catService.setOwnerById(catId, ownerId);
                return ResponseEntity.ok("Successfully set new owner.");
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("id")
    public ResponseEntity<Object> delete(@RequestParam String id) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                catService.delete(id);
                return ResponseEntity.ok("Cat successfully deleted.");
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
