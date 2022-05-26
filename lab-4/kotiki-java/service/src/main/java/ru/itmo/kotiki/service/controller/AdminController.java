package ru.itmo.kotiki.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.service.dto.UserDto;
import ru.itmo.kotiki.service.service.*;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private UserStorageService userStorageService;

    @PostMapping("/users/save")
    public ResponseEntity saveUser(@RequestBody UserDto userDto) {
        try {
            userStorageService.saveUser(userDto);
            return ResponseEntity.ok("User successfully saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/users/owner")
    public ResponseEntity setOwnerToUser(@RequestParam String userId, @RequestParam String ownerId) {
        try {
            userStorageService.setOwnerToUser(userId, ownerId);
            return ResponseEntity.ok("Owner successfully set to user.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users/all")
    public ResponseEntity allUsers() {
        try {
            return ResponseEntity.ok(userStorageService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity deleteUser(@RequestParam String userId) {
        try {
            userStorageService.deleteUser(userId);
            return ResponseEntity.ok("User successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
