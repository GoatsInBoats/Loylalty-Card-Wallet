package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.models.Reward;
import com.github.loyaltycardwallet.services.RewardService;
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
@RequestMapping("/api/rewards")
public class RewardController {
    private RewardService rewardService;

    @GetMapping
    public ResponseEntity<List<Reward>> getAll() {
        return ResponseEntity.ok(rewardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reward> getById(@PathVariable UUID id) {
        return rewardService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reward> add(@RequestBody @Valid Reward reward) {
        Reward newReward = rewardService.save(reward);
        return ResponseEntity.created(URI.create("/" + newReward.getId())).body(newReward);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reward> update(@PathVariable UUID id, @RequestBody @Valid Reward reward) {
        if (!rewardService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        reward.setId(id);
        rewardService.save(reward);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        rewardService.deleteById(id);
        if (!rewardService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
