package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.models.StampCardReward;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StampCardRewardService {

    List<StampCardReward> findAll();

    Optional<StampCardReward> findById(UUID id);

    StampCardReward save(StampCardReward stampCardReward);

    void deleteById(UUID id);

    boolean existById(UUID id);

}
