package com.demo.cosmossessiontoken.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.List;
import org.springframework.context.ApplicationContext;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.demo.cosmossessiontoken.Configurations.CosmosDbConfiguration;
import com.demo.cosmossessiontoken.models.Transaction;
import com.azure.spring.data.cosmos.core.mapping.Container;

public class CustomRepositoryImpl implements CustomRepository {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CosmosDbConfiguration cosmosDbConfig;

    private CosmosContainer container;

    @Override
    public Optional<Transaction> findById(String id, String accountId, String sessionToken) {

        if(container == null) {
            container = context
                .getBean(CosmosClient.class)
                .getDatabase(cosmosDbConfig.getDatabaseName())
                .getContainer(getContainerName(Transaction.class));
        }

        CosmosItemRequestOptions options = new CosmosItemRequestOptions();
        options.setSessionToken(sessionToken);

        return Optional.ofNullable(container.readItem(id, new PartitionKey(accountId), options, Transaction.class).getItem());
    }

    @Override
    public Optional<List<Transaction>> findAll(String accountId, String sessionToken) {

        if(container == null) {
            container = context
                .getBean(CosmosClient.class)
                .getDatabase(cosmosDbConfig.getDatabaseName())
                .getContainer(getContainerName(Transaction.class));
        }

        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        options.setSessionToken(sessionToken);

        return Optional.ofNullable(container.readAllItems(new PartitionKey(accountId), options, Transaction.class).stream().toList());
    }

    @Override
    public String saveWithSessionToken(Transaction transaction) {

        if(container == null) {
            container = context
                .getBean(CosmosClient.class)
                .getDatabase(cosmosDbConfig.getDatabaseName())
                .getContainer(getContainerName(Transaction.class));
        }

        CosmosItemRequestOptions options = new CosmosItemRequestOptions();
        CosmosItemResponse<Transaction> response = container.upsertItem(transaction, new PartitionKey(transaction.getAccountId()), options);

        return response.getSessionToken();
    }

    private String getContainerName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Container.class)) {
            Container container = clazz.getAnnotation(Container.class);
            return container.containerName();
        }
        throw new IllegalStateException("No @Container annotation found on class " + clazz.getName());
    }
    
}
