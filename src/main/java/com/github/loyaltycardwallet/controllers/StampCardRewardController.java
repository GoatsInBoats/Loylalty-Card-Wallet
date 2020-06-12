package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.models.StampCardReward;
import com.github.loyaltycardwallet.services.StampCardRewardService;
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
@RequestMapping("/api/stampcards-rewards")
public class StampCardRewardController {

    private final StampCardRewardService stampCardRewardService;

    @GetMapping
    public ResponseEntity<List<StampCardReward>> getAll() {
        return ResponseEntity.ok(stampCardRewardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StampCardReward> getById(@PathVariable UUID id) {
        return stampCardRewardService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampCardReward> add(@RequestBody @Valid StampCardReward stampCardReward) {
        StampCardReward newStampCardReward = stampCardRewardService.save(stampCardReward);
        return ResponseEntity.created(URI.create("/" + newStampCardReward.getId())).body(newStampCardReward);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampCardReward> update(@PathVariable UUID id, @RequestBody @Valid StampCardReward stampCardReward) {
        if (!stampCardRewardService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        stampCardReward.setId(id);
        stampCardRewardService.save(stampCardReward);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        stampCardRewardService.deleteById(id);
        if (!stampCardRewardService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
