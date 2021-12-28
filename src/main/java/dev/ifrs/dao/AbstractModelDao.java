package dev.ifrs.dao;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.inject.Inject;

import dev.ifrs.model.AbstractDynamoWrapper;

public abstract class AbstractModelDao<M extends AbstractDynamoWrapper> {

  public String tableName;
  public Class<M> modelClass;
  public DynamoDB dynamo = null;
  public Table table = null;

  @Inject
  public ModelDaoProvider modelDaoProvider;

  public AbstractModelDao() {
  }

  public AbstractModelDao(final Class<M> modelClass,
                          final String tableName) {
    this.modelClass = modelClass;
    this.tableName = tableName;
  }

  public DynamoDB getDynamo() {
    if (this.dynamo == null) {
      this.dynamo = new DynamoDB(this.modelDaoProvider.getDynamoClient());
    }
    return this.dynamo;
  }

  public Table getTable() {
    if (this.table == null) {
      this.table = this.getDynamo().getTable(this.tableName);
    }
    return this.table;
  }

  protected ItemCollection<QueryOutcome> queryItems(List<Pair<String, Object>> params) {

    final NameMap queryNames = new NameMap();
    final ValueMap queryValues = new ValueMap();

    final List<String> queryParams = new ArrayList<>();
    for (final Pair<String, Object> param : params) {
      String paramName = "#n_" + param.getKey();
      String paramValue = ":v_" + param.getKey();

      queryValues.with(paramValue, param.getValue());
      queryNames.with(paramName, param.getKey());

      queryParams.add(paramName + " = " + paramValue);
    }

    final QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(String.join(" AND ", queryParams));
    querySpec.withNameMap(queryNames).withValueMap(queryValues);

    return getDynamo().getTable(tableName).query(querySpec);
  }

  public void createModelItemIfNotExists(final M model,
                                         final List<String> conditionAttrs) throws Exception {

    StringJoiner conditions = new StringJoiner(" AND ");
    for (final String conditionAttr : conditionAttrs) {
      conditions.add(String.format("attribute_not_exists(%s)", conditionAttr));
    }

    final Item dynamoItem = model.toDynamoItem();
    final PutItemSpec putItemSpec = new PutItemSpec()
        .withItem(dynamoItem)
        .withConditionExpression(conditions.toString())
        .withReturnValues(ReturnValue.NONE);
    try {
      this.getTable().putItem(putItemSpec);
    } catch (ConditionalCheckFailedException ex) {
      throw new Exception("Model already exists", ex);
    }
  }

  protected void updateItem(final PrimaryKey primaryKey,
                            final String expression,
                            final ValueMap valueMap,
                            final String conditions) {
    final UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(primaryKey)
        .withUpdateExpression(expression)
        .withValueMap(valueMap)
        .withConditionExpression(conditions)
        .withReturnValues(ReturnValue.NONE);
    this.getTable().updateItem(updateItemSpec);
  }

  protected void deleteItem(final PrimaryKey primaryKey) {
    this.getTable().deleteItem(primaryKey);
  }

}
