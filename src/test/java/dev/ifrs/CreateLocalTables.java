package dev.ifrs;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;

@QuarkusTest
@ApplicationScoped
public class CreateLocalTables {

  @Inject
  DynamoDbClient dynamoDb;

  @Test
  public void testHelloEndpoint() {
    CreateTableRequest request = CreateTableRequest.builder()
        .keySchema(KeySchemaElement.builder()
                .attributeName("userId")
                .keyType(KeyType.HASH)
                .build(),
            KeySchemaElement.builder()
                .attributeName("type")
                .keyType(KeyType.RANGE)
                .build())
        .tableName("NextAction")
        .build();
    try {
      dynamoDb.createTable(request);
    } catch (DynamoDbException ex) {
      System.out.println("failed to create table");
    }
  }
}
