package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.models.StampCardProgress;
import com.github.loyaltycardwallet.repositories.StampCardProgressRepository;
import com.github.loyaltycardwallet.services.StampCardProgressService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Service
class StampCardProgressServiceImpl implements StampCardProgressService {
    private StampCardProgressRepository stampCardProgressRepository;

    @Override
    public List<StampCardProgress> findAll() {
        return stampCardProgressRepository.findAll();
    }

    @Override
    public StampCardProgress save(StampCardProgress scp) {
        return stampCardProgressRepository.save(scp);
    }

    @Override
    public Optional<StampCardProgress> findById(UUID id) {
        return stampCardProgressRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        stampCardProgressRepository.deleteById(id);
    }

    @Override
    public boolean existById(UUID id) {
        return stampCardProgressRepository.existsById(id);
    }
}
