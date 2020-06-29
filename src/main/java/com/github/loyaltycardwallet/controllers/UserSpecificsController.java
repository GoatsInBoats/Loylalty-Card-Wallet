package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.models.UserSpecifics;
import com.github.loyaltycardwallet.services.UserSpecificsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users-specifics")
public class UserSpecificsController {

    private UserSpecificsService userSpecificsService;

    @GetMapping
    public ResponseEntity<List<UserSpecifics>> getAll() {
        return ResponseEntity.ok(userSpecificsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSpecifics> getById(@PathVariable UUID id) {

        return userSpecificsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSpecifics> add(@RequestBody @Valid UserSpecifics userSpecifics) {
        UserSpecifics newUserSpecifics = userSpecificsService.save(userSpecifics);
        return ResponseEntity.created(URI.create("/" + newUserSpecifics.getId())).body(newUserSpecifics);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSpecifics> update(@PathVariable UUID id, @RequestBody @Valid UserSpecifics userSpecifics) {
        if (!userSpecificsService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        userSpecifics.setId(id);
        userSpecificsService.save(userSpecifics);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        userSpecificsService.deleteById(id);
        if (!userSpecificsService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
