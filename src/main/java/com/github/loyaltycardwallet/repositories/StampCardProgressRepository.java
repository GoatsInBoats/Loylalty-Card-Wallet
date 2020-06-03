package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.StampCardProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StampCardProgressRepository extends JpaRepository<StampCardProgress, UUID> {
    boolean existsById(UUID id);
}
