package dev.ifrs.dao;

import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
@Provider
public class ModelDaoProvider {

    @ConfigProperty(name = "dynamo.endpoint", defaultValue = "")
    private String dynamoEndpoint;

    @ConfigProperty(name = "dynamo.port", defaultValue = "")
    private String dynamoPort;

    @ConfigProperty(name = "dynamo.region", defaultValue = "")
    private String dynamoRegion;

    private AmazonDynamoDB dynamoClient = null;

    public ModelDaoProvider() {

    }

    public AmazonDynamoDB getDynamoClient() {
        if (this.dynamoClient == null) {
            this.dynamoClient = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration("http://" + dynamoEndpoint + ":" + dynamoPort,
                                    dynamoRegion))
                    .build();
        }
        return this.dynamoClient;
    }
}
