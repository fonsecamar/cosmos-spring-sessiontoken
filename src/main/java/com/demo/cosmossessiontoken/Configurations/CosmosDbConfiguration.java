package com.demo.cosmossessiontoken.Configurations;

import com.azure.cosmos.CosmosClientBuilder;
import com.demo.cosmossessiontoken.repositories.TransactionRepository;
import com.azure.cosmos.CosmosClient;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCosmosRepositories(basePackageClasses = TransactionRepository.class)
public class CosmosDbConfiguration extends AbstractCosmosConfiguration {

    @Value("${azure.cosmos.uri}")
    private String cosmosUri;

    @Value("${azure.cosmos.key}")
    private String cosmosKey;

    @Value("${azure.cosmos.database}")
    private String databaseName;

    @Value("${azure.cosmos.preferred-regions}")
    private String preferredRegions;

    @Value("${azure.cosmos.multipleWriteRegionsEnabled}")
    private Boolean multipleWriteRegionsEnabled;

    @Bean
    public CosmosClientBuilder getCosmosClientBuilder() {
        List<String> preferredRegionsList = Arrays.asList(preferredRegions.split(","));

        System.out.println("cosmosUri exists: " + (cosmosUri != null && !cosmosUri.isEmpty()));
        System.out.println("cosmosKey exists: " + (cosmosKey != null && !cosmosKey.isEmpty()));
        System.out.println("preferredRegionsList: " + preferredRegionsList);

        return new CosmosClientBuilder()
                .endpoint(cosmosUri)
                .key(cosmosKey)
                .multipleWriteRegionsEnabled(multipleWriteRegionsEnabled)
                .preferredRegions(preferredRegionsList)
                .directMode();
    }

    @Bean
    public CosmosClient cosmosClient() {
        return getCosmosClientBuilder().buildClient();
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }
}