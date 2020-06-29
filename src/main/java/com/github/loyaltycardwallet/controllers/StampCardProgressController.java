package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.models.StampCardProgress;
import com.github.loyaltycardwallet.services.StampCardProgressService;
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
@RequestMapping("/api/stampcards-progresses")
public class StampCardProgressController {

    private StampCardProgressService stampCardProgressService;

    @GetMapping
    public ResponseEntity<List<StampCardProgress>> getAll() {
        return ResponseEntity.ok(stampCardProgressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StampCardProgress> getById(@PathVariable UUID id) {
        return stampCardProgressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampCardProgress> add(@RequestBody @Valid StampCardProgress stampCardProgress) {
        StampCardProgress newStampCardProgress = stampCardProgressService.save(stampCardProgress);
        return ResponseEntity.created(URI.create("/" + newStampCardProgress.getId())).body(newStampCardProgress);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampCardProgress> updateUser(@PathVariable UUID id, @RequestBody @Valid StampCardProgress stampCardProgress) {
        if (!stampCardProgressService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        stampCardProgress.setId(id);
        stampCardProgressService.save(stampCardProgress);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        stampCardProgressService.deleteById(id);
        if (!stampCardProgressService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
