package com.demo.cosmossessiontoken;

import com.azure.core.annotation.QueryParam;
import com.demo.cosmossessiontoken.models.Transaction;
import com.demo.cosmossessiontoken.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class CosmosdbspringApplication {

	@Autowired
	private TransactionRepository transactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(CosmosdbspringApplication.class, args);
	}

	@GetMapping(value = "/transaction/{accountId}/{id}", produces = "application/json") 
	public Optional<Transaction> getTransaction(@PathVariable String accountId, @PathVariable String id, @QueryParam("sessionToken") String sessionToken) throws Exception { 
		return transactionRepository.findById(id, accountId, sessionToken);
	}

	@GetMapping(value = "/transaction/{accountId}", produces = "application/json") 
	public Optional<List<Transaction>> listTransaction(@PathVariable String accountId, @QueryParam("sessionToken") String sessionToken) throws Exception { 
		return transactionRepository.findAll(accountId, sessionToken);
	}

	@PostMapping(value = "/transaction", consumes = "application/json")
	public String createTransaction(@RequestBody(required = true) Transaction transaction) throws Exception {

		transaction.setDateTime(LocalDateTime.now());
		final String sessionToken = transactionRepository.saveWithSessionToken(transaction);
		return java.net.URLEncoder.encode(sessionToken, "UTF-8");
	}
}