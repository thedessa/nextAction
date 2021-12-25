package dev.ifrs.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import software.amazon.awssdk.utils.Pair;

public abstract class AbstractModelDao<M> {

    public String tableName;
    public Class<M> modelClass;
    public DynamoDB dynamo = null;
    public Table table = null;

    @Inject
    public ModelDaoProvider modelDaoProvider;

    public AbstractModelDao() {
    }

    public AbstractModelDao(final Class<M> modelClass, final String tableName) {
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

    protected List<Item> batchGetItems(final List<PrimaryKey> itemsKeys) {
        final List<Item> batchGetResult = new ArrayList<>();
        List<PrimaryKey> toDoRequest = new ArrayList<>(new HashSet<>(itemsKeys));

        final TableKeysAndAttributes tableKeysAndAttributes = new TableKeysAndAttributes("NextAction");
        toDoRequest.forEach(tableKeysAndAttributes::addPrimaryKey);

        BatchGetItemOutcome outcome = getDynamo().batchGetItem(tableKeysAndAttributes);
        outcome.getTableItems().values().forEach(batchGetResult::addAll);

        return batchGetResult;
    }

    protected ItemCollection<QueryOutcome> queryItems(List<Pair<String, Object>> params) {

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

        return getDynamo().getTable("NextAction").query(querySpec);
    }

}
