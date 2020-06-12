package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.StampCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StampCardRepository extends JpaRepository<StampCard, UUID> {

    boolean existsById(UUID id);
}
