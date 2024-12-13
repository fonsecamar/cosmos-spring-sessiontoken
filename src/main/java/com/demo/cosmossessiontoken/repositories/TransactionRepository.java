package com.demo.cosmossessiontoken.repositories;

import org.springframework.stereotype.Repository;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.demo.cosmossessiontoken.models.Transaction;

@Repository
public interface TransactionRepository extends CosmosRepository <Transaction,String>, CustomRepository {

}