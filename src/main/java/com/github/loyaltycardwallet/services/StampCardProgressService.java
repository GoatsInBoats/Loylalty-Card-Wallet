package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.models.StampCardProgress;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StampCardProgressService {
    List<StampCardProgress> findAll();

    Optional<StampCardProgress> findById(UUID id);

    StampCardProgress save(StampCardProgress stampCardProgress);

    void deleteById(UUID id);

    boolean existById(UUID id);

}
