package com.demo.cosmossessiontoken.models;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

@Container(containerName = "transactions", autoCreateContainer = true, autoScale = true, ru = "1000")

public class Transaction {

    public Transaction() {
    }    
    
    @Id
    private String id;

    @PartitionKey
    private String accountId;

    private String merchant;
    private String amount;
    private LocalDateTime dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}