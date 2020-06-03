package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.models.UserSpecifics;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSpecificsService {
    List<UserSpecifics> findAll();

    Optional<UserSpecifics> findById(UUID id);

    UserSpecifics save(UserSpecifics user);

    void deleteById(UUID id);

    boolean existById(UUID id);

}
