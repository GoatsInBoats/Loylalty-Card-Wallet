package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.models.StampCard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface StampCardService {
    List<StampCard> findAll();

    Optional<StampCard> findById(UUID id);

    StampCard save(StampCard stampCard);

    void deleteById(UUID id);

    boolean existById(UUID id);

}
