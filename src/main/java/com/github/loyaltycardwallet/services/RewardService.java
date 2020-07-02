package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.models.Reward;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RewardService {

    List<Reward> findAll();

    Optional<Reward> findById(UUID id);

    Reward save(Reward reward);

    void deleteById(UUID id);

    boolean existById(UUID id);
}
