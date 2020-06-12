package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.models.StampCard;
import com.github.loyaltycardwallet.services.StampCardService;
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
@RequestMapping("/api/stampcards")
public class StampCardController {

    private final StampCardService stampCardService;

    @GetMapping
    public ResponseEntity<List<StampCard>> getAll() {
        return ResponseEntity.ok(stampCardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StampCard> getById(@PathVariable UUID id) {
        return stampCardService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampCard> add(@RequestBody @Valid StampCard stampCard) {
        StampCard newStampCard = stampCardService.save(stampCard);
        return ResponseEntity.created(URI.create("/" + newStampCard.getId())).body(newStampCard);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampCard> updateUser(@PathVariable UUID id, @RequestBody @Valid StampCard stampCard) {
        if (!stampCardService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        stampCard.setId(id);
        stampCardService.save(stampCard);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        stampCardService.deleteById(id);
        if (!stampCardService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
