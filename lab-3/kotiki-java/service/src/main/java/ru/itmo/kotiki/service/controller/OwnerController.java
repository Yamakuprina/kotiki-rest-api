package ru.itmo.kotiki.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.service.dto.OwnerDto;
import ru.itmo.kotiki.service.service.OwnerService;
import ru.itmo.kotiki.service.userDetails.UserRole;

import java.util.Collection;

@RestController
@RequestMapping("/owners/")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("all")
    public ResponseEntity AllOwners() {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(ownerService.getAllOwners());
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("cats")
    public ResponseEntity CatsByOwnerId(@RequestParam String ownerId) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(ownerService.getCatsByOwnerId(ownerId));
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("save")
    public ResponseEntity save(@RequestBody OwnerDto ownerDto) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                ownerService.save(ownerDto);
                return ResponseEntity.ok("Owner successfully saved.");
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id")
    public ResponseEntity ById(@RequestParam String id) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                return ResponseEntity.ok(ownerService.findById(id));
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("id")
    public ResponseEntity delete(@RequestParam String id) {
        try {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))){
                ownerService.delete(id);
                return ResponseEntity.ok("Owner successfully deleted.");
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
