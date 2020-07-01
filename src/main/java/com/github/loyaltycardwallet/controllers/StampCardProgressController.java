package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.dto.ActualScoreIncrementDTO;
import com.github.loyaltycardwallet.models.StampCardProgress;
import com.github.loyaltycardwallet.models.User;
import com.github.loyaltycardwallet.services.StampCardProgressService;
import com.github.loyaltycardwallet.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/stampcards-progresses")
public class StampCardProgressController {

    private StampCardProgressService stampCardProgressService;
    private UserService userService;

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

    @PutMapping(value = "/{userId}/{stampCardId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StampCardProgress> addPointToStampCard(@PathVariable UUID userId,
                                                                 @PathVariable UUID stampCardId,
                                                                 @RequestBody @Valid ActualScoreIncrementDTO actualScoreIncrementDTO) {

        User user = null;
        if (!userService.existById(userId)) {
            log.error(new StringBuilder().append("User with id ").append(userId.toString()).append(" not found").toString());
            return ResponseEntity.notFound().build();
        } else {
            user = userService.findById(userId)
                    .orElseThrow(NoSuchElementException::new);
        }

        List<StampCardProgress> stampCardProgresses = user
                .getUserSpecifics()
                .getStampCardProgresses()
                .stream()
                .filter(stampCardProgress -> stampCardProgress.getStampCard().getId().equals(stampCardId))
                .collect(Collectors.toList());

        StampCardProgress stampCardProgress = stampCardProgresses.get(0);

        if (stampCardProgress.getActualScore() == 10) {
            stampCardProgress.setActualScore(1);
        } else {
            stampCardProgress.setActualScore(stampCardProgress.getActualScore() + 1);
        }

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
