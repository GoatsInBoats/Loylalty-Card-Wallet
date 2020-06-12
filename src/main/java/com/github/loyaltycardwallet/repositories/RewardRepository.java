package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardRepository extends JpaRepository<Reward, UUID> {

    boolean existsById(UUID id);
}
