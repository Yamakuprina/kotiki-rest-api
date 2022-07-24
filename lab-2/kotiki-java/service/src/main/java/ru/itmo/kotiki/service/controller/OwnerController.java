package ru.itmo.kotiki.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.service.dto.OwnerDto;
import ru.itmo.kotiki.service.service.OwnerService;

@RestController
@RequestMapping("/owners/")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("all")
    public ResponseEntity AllOwners() {
        try {
            return ResponseEntity.ok(ownerService.getAllOwners());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("cats")
    public ResponseEntity CatsByOwnerId(@RequestParam String ownerId) {
        try {
            return ResponseEntity.ok(ownerService.getCatsByOwnerId(ownerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("save")
    public ResponseEntity save(@RequestBody OwnerDto ownerDto) {
        try {
            ownerService.save(ownerDto);
            return ResponseEntity.ok("Owner successfully saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("id")
    public ResponseEntity ById(@RequestParam String id) {
        try {
            return ResponseEntity.ok(ownerService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("id")
    public ResponseEntity delete(@RequestParam String id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.ok("Owner successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
