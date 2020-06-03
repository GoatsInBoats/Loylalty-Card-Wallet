package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.models.UserSpecifics;
import com.github.loyaltycardwallet.repositories.UserSpecificsRepository;
import com.github.loyaltycardwallet.services.UserSpecificsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserSpecificsServiceImpl implements UserSpecificsService {

    private UserSpecificsRepository userSpecificsRepository;

    public UserSpecificsServiceImpl(UserSpecificsRepository userSpecificsRepository) {
        this.userSpecificsRepository = userSpecificsRepository;
    }

    @Override
    public List<UserSpecifics> findAll() {
        return userSpecificsRepository.findAll();
    }

    @Override
    public UserSpecifics save(UserSpecifics user) {
        return userSpecificsRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        userSpecificsRepository.deleteById(id);
    }

    @Override
    public Optional<UserSpecifics> findById(UUID id) {
        return userSpecificsRepository.findById(id);
    }

    @Override
    public boolean existById(UUID id) {
        return userSpecificsRepository.existsById(id);
    }
}

