package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.dto.ManagerEditDTO;
import com.github.loyaltycardwallet.dto.NormalUserEditDTO;
import com.github.loyaltycardwallet.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(UUID id);

    User save(User user);

    void deleteById(UUID id);

    User findByUsername(String username);

    boolean existById(UUID id);

    void editNormalUserFields(NormalUserEditDTO normalUserEditDTO, User normalUser);

    void editManagerFields(ManagerEditDTO managerEditDTO, User manager) throws IOException;
}

