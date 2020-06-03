package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.models.StampCard;
import com.github.loyaltycardwallet.repositories.StampCardRepository;
import com.github.loyaltycardwallet.services.StampCardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StampCardServiceImpl implements StampCardService {

    private StampCardRepository stampCardRepository;

    public StampCardServiceImpl(StampCardRepository stampCardRepository) {
        this.stampCardRepository = stampCardRepository;
    }

    @Override
    public List<StampCard> findAll() {
        return stampCardRepository.findAll();
    }

    @Override
    public StampCard save(StampCard company) {
        return stampCardRepository.save(company);
    }

    @Override
    public Optional<StampCard> findById(UUID id) {
        return stampCardRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        stampCardRepository.deleteById(id);
    }

    @Override
    public boolean existById(UUID id) {
        return stampCardRepository.existsById(id);
    }
}
