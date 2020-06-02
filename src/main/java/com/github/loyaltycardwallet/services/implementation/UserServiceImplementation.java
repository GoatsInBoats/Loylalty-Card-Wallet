package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.models.User;
import com.github.loyaltycardwallet.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImplementation implements UserService {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteById(UUID id) throws Exception {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public boolean existById(UUID id) {
        return false;
    }
}
