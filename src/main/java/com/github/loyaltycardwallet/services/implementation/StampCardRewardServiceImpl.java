package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.models.StampCardReward;
import com.github.loyaltycardwallet.repositories.StampCardRewardRepository;
import com.github.loyaltycardwallet.services.StampCardRewardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StampCardRewardServiceImpl implements StampCardRewardService {

    private StampCardRewardRepository stampCardRewardRepository;

    public StampCardRewardServiceImpl(StampCardRewardRepository stampCardRewardRepository) {
        this.stampCardRewardRepository = stampCardRewardRepository;
    }

    @Override
    public List<StampCardReward> findAll() {
        return stampCardRewardRepository.findAll();
    }

    @Override
    public StampCardReward save(StampCardReward stampCardReward) {
        return stampCardRewardRepository.save(stampCardReward);
    }


    @Override
    public Optional<StampCardReward> findById(UUID id) {
        return stampCardRewardRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        stampCardRewardRepository.deleteById(id);
    }

    @Override
    public boolean existById(UUID id) {
        return stampCardRewardRepository.existsById(id);
    }
}
