package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.models.Reward;
import com.github.loyaltycardwallet.repositories.RewardRepository;
import com.github.loyaltycardwallet.services.RewardService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Service
public class RewardServcieImpl implements RewardService {

    private RewardRepository rewardRepository;

    @Override
    public List<Reward> findAll() {
        return rewardRepository.findAll();
    }

    @Override
    public Reward save(Reward stampCardReward) {
        return rewardRepository.save(stampCardReward);
    }

    @Override
    public Optional<Reward> findById(UUID id) {
        return rewardRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        rewardRepository.deleteById(id);
    }

    @Override
    public boolean existById(UUID id) {
        return rewardRepository.existsById(id);
    }

}
