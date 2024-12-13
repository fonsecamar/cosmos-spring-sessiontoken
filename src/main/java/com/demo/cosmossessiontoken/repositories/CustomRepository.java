package com.demo.cosmossessiontoken.repositories;

import com.demo.cosmossessiontoken.models.Transaction;
import java.util.Optional;
import java.util.List;

public interface CustomRepository {

    Optional<Transaction> findById(String id, String accountId, String sessionToken);
    Optional<List<Transaction>> findAll(String accountId, String sessionToken);
    String saveWithSessionToken(Transaction transaction);
    
}
