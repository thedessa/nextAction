package dev.ifrs.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import dev.ifrs.model.NextAction;

@ApplicationScoped
public class NextActionDAOImpl extends AbstractModelDao<NextAction> implements NextActionDAO {

  private static final Logger logger = LoggerFactory.getLogger(NextActionDAOImpl.class);

  public static final String TABLE_NAME = "NextAction";

  public NextActionDAOImpl() {
    super(NextAction.class, TABLE_NAME);
  }

  @Override
  public List<NextAction> listTasks(String userId) {
    ItemCollection<QueryOutcome> queryItems = queryItems(Collections.singletonList(Pair.of(NextAction.ATTR_USER_ID, userId)));
    final List<NextAction> nextActions = new ArrayList<>();
    for (Item dynamoItem : queryItems) {
      nextActions.add(new NextAction(dynamoItem));
    }
    return nextActions;
  }

  @Override
  public void addTask(String userId, final int type, final String title) {
    final NextAction nextAction = new NextAction();
    nextAction.setUserId(userId);
    nextAction.setTaskId(UUID.randomUUID().toString());
    nextAction.setType(type);
    nextAction.setTitle(title);
    nextAction.setCompleted(Boolean.FALSE);
    nextAction.setContext("noContext");

    try {
      createModelItemIfNotExists(nextAction, Arrays.asList(NextAction.ATTR_USER_ID, NextAction.ATTR_TASK_ID));
    } catch (Exception e) {
      logger.error("Error creating task for user {}, type {}, title {}", userId, type, title, e);
    }

  }

  @Override
  public void completeTask(String userId, String taskId) {

    final PrimaryKey primaryKey = new PrimaryKey(NextAction.ATTR_USER_ID, userId, NextAction.ATTR_TASK_ID, taskId);
    final String expression = "SET completed = :b";
    final ValueMap valueMap = new ValueMap().withBoolean(":b", Boolean.TRUE);
    final String conditions = String.format("attribute_exists(%s) AND attribute_exists(%s)", NextAction.ATTR_USER_ID, NextAction.ATTR_TASK_ID);

    updateItem(primaryKey, expression, valueMap, conditions);
  }

  @Override
  public void renameTask(String userId, final String taskId, String newTitle) {
    final PrimaryKey primaryKey = new PrimaryKey(NextAction.ATTR_USER_ID, userId, NextAction.ATTR_TASK_ID, taskId);
    final String expression = "SET title = :s";
    final ValueMap valueMap = new ValueMap().withString(":s", newTitle);
    final String conditions = String.format("attribute_exists(%s) AND attribute_exists(%s)", NextAction.ATTR_USER_ID, NextAction.ATTR_TASK_ID);

    updateItem(primaryKey, expression, valueMap, conditions);
  }

  @Override
  public void updateContext(final String userId,
                            final String taskId,
                            final String newContext) {
    final PrimaryKey primaryKey = new PrimaryKey(NextAction.ATTR_USER_ID, userId, NextAction.ATTR_TASK_ID, taskId);
    final String expression = "SET context = :s";
    final ValueMap valueMap = new ValueMap().withString(":s", newContext);
    final String conditions = String.format("attribute_exists(%s) AND attribute_exists(%s)", NextAction.ATTR_USER_ID, NextAction.ATTR_TASK_ID);

    updateItem(primaryKey, expression, valueMap, conditions);
  }

  @Override
  public void deleteTask(String userId,
                         String taskId) {
    deleteItem(new PrimaryKey(NextAction.ATTR_USER_ID, userId, NextAction.ATTR_TASK_ID, taskId));
  }
}
