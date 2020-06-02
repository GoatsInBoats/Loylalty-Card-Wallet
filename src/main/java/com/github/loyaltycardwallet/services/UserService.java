package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(UUID id);

    User save(User user);

    void deleteById(UUID id) throws Exception;

    User findByUsername(String username);

    boolean existById(UUID id);
}

