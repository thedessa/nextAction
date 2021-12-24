package dev.ifrs.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import dev.ifrs.NextAction;
import dev.ifrs.NextActionState;
import software.amazon.awssdk.utils.Pair;

@ApplicationScoped
public class NextActionDAOImpl implements NextActionDAO {

  public DynamoDB dynamo = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://192.168.33.100:4569", "us-west-2"))
      .build());

  @Override
  public List<NextAction> listInboxTasks(String userId) {
    ItemCollection<QueryOutcome> queryItems = queryItems(Pair.of("userId", userId));
    final List<NextAction> nextActions = new ArrayList<>();
    for (Item dynamoItem : queryItems) {

      nextActions.add(new NextAction(dynamoItem));
    }

    return nextActions;
  }

  @Override
  public int getTask(final String userId) {
    final GetItemSpec getItem = new GetItemSpec().withConsistentRead(true)
        .withPrimaryKey(new PrimaryKey("userId", userId, "type", 2));
    Item item = dynamo.getTable("NextAction").getItem(getItem);
    return new NextAction(item).getType();
  }

  @Override
  public Map<String, NextActionState> listNextActionTasks(String userId) {
    return Collections.singletonMap(UUID.randomUUID().toString(),
        new NextActionState("Next Action Task", Boolean.TRUE));
  }

  @Override
  public void addTask(String userId, final int type, String title) {
    // Add task to map
    // dynamoDB.executeStatement(...)
  }

  @Override
  public void completeTask(String userId, final int type, String taskId) {
    // Complete task
  }

  @Override
  public void renameTask(String userId, final int type, String newTitle) {
    // Rename task
  }

  @Override
  public void deleteTask(String userId, final int type, String taskId) {
    // Delete task
  }

  public List<Item> batchGetItems(final List<PrimaryKey> itemsKeys) {

    // to do handle max batch get size
    final List<Item> batchGetResult = new ArrayList<>();
    List<PrimaryKey> toDoRequest = new ArrayList<>(new HashSet<>(itemsKeys));

    final TableKeysAndAttributes tableKeysAndAttributes = new TableKeysAndAttributes("NextAction");
    toDoRequest.forEach(tableKeysAndAttributes::addPrimaryKey);

    BatchGetItemOutcome outcome = dynamo.batchGetItem(tableKeysAndAttributes);
    outcome.getTableItems().values().forEach(batchGetResult::addAll);

    return batchGetResult;
  }

  public ItemCollection<QueryOutcome> queryItems(final Pair<String, Object>... params) {

    final NameMap queryNames = new NameMap();
    final ValueMap queryValues = new ValueMap();

    final List<String> queryParams = new ArrayList<>();
    for (final Pair<String, Object> param : params) {
      String paramName = "#n_" + param.left();
      String paramValue = ":v_" + param.left();

      queryValues.with(paramValue, param.right());
      queryNames.with(paramName, param.left());

      queryParams.add(paramName + " = " + paramValue);
    }

    final QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(String.join(" AND ", queryParams));
    querySpec.withNameMap(queryNames).withValueMap(queryValues);

    return dynamo.getTable("NextAction").query(querySpec);
  }
}
