package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.UserSpecifics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSpecificsRepository extends JpaRepository<UserSpecifics, UUID> {
    boolean existsById(UUID id);
}
