package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.StampCardReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StampCardRewardRepository extends JpaRepository<StampCardReward, UUID> {

    boolean existsById(UUID id);
}
