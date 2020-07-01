package com.github.loyaltycardwallet.controllers;


import com.github.loyaltycardwallet.dto.ManagerRegisterAndEditDTO;
import com.github.loyaltycardwallet.dto.NormalUserRegisterAndEditDTO;
import com.github.loyaltycardwallet.models.User;
import com.github.loyaltycardwallet.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok((userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.created(URI.create("/" + createdUser.getId())).body(createdUser);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody @Valid User user) {
        if (!userService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        userService.save(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable UUID id) {
        userService.deleteById(id);
        if (!userService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("roles/{role}")
    public ResponseEntity<List<User>> getUsersWithRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .filter(user -> user.getRoles().equals(role.toUpperCase()))
                .collect(Collectors.toList()));
    }

    @PutMapping(value = "/edit/normal/{id}")
    public ResponseEntity<User> updateNormalUser(@PathVariable UUID id, @RequestBody @Valid NormalUserRegisterAndEditDTO normalUserEditDTO) {
        User normalUser = null;
        if (!userService.existById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            normalUser = userService.findById(id)
                    .orElseThrow(NoSuchElementException::new);
        }

        userService.editNormalUserFields(normalUserEditDTO, normalUser);

        userService.save(normalUser);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/edit/manager/{id}")
    public ResponseEntity<User> updateManager(@PathVariable UUID id, @RequestBody @Valid ManagerRegisterAndEditDTO managerEditDTO) throws IOException {
        User manager = null;
        if (!userService.existById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            manager = userService.findById(id)
                    .orElseThrow(NoSuchElementException::new);
        }

        userService.editManagerFields(managerEditDTO, manager);

        userService.save(manager);

        return ResponseEntity.noContent().build();
    }

}
