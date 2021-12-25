package dev.ifrs.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;

import dev.ifrs.NextAction;
import dev.ifrs.NextActionState;
import software.amazon.awssdk.utils.Pair;

@ApplicationScoped
public class NextActionDAOImpl extends AbstractModelDao<NextAction> implements NextActionDAO {

  public static final String TABLE_NAME = "NextAction";

  public NextActionDAOImpl() {
    super(NextAction.class, TABLE_NAME);
  }

  @Override
  public List<NextAction> listInboxTasks(String userId) {
    ItemCollection<QueryOutcome> queryItems = queryItems(Arrays.asList(Pair.of("userId", userId)));
    final List<NextAction> nextActions = new ArrayList<>();
    for (Item dynamoItem : queryItems) {
      nextActions.add(new NextAction(dynamoItem));
    }
    return nextActions;
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
}
