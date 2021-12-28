package dev.ifrs.model;

import com.amazonaws.services.dynamodbv2.document.Item;

import io.quarkus.runtime.annotations.RegisterForReflection;

import org.apache.commons.lang3.builder.ToStringBuilder;

@RegisterForReflection
public class NextAction extends AbstractDynamoWrapper {

  public static final String ATTR_USER_ID = "userId";
  public static final String ATTR_TASK_ID = "taskId";
  public static final String ATTR_TYPE = "type";
  public static final String ATTR_TITLE = "title";
  public static final String ATTR_COMPLETED = "completed";
  public static final String ATTR_CONTEXT = "context";

  private String userId; // partition key
  private String taskId; // sort key
  private int type;
  private String title;
  private Boolean isCompleted;
  private String context;

  public NextAction() {

  }

  public NextAction(final Item item) {
    this.userId = item.getString(ATTR_USER_ID);
    this.taskId = item.getString(ATTR_TASK_ID);
    this.type = item.getNumber(ATTR_TYPE).intValue();
    this.title = item.getString(ATTR_TITLE);
    this.isCompleted = item.getBOOL(ATTR_COMPLETED);
    this.context = item.getString(ATTR_CONTEXT);
  }

  public NextAction(final String userId,
                    final String taskId,
                    final int type,
                    final String title,
                    final Boolean isCompleted) {
    this.userId = userId;
    this.taskId = taskId;
    this.type = type;
    this.title = title;
    this.isCompleted = isCompleted;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(final String taskId) {
    this.taskId = taskId;
  }

  public int getType() {
    return type;
  }

  public void setType(final int type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return isCompleted;
  }

  public void setCompleted(final Boolean completed) {
    isCompleted = completed;
  }

  public String getContext() {
    return context;
  }

  public void setContext(final String context) {
    this.context = context;
  }

  @Override
  public Item toDynamoItem() {
    final Item item = new Item()
        .withPrimaryKey(ATTR_USER_ID, userId, ATTR_TASK_ID, taskId)
        .withString(ATTR_TITLE, title)
        .withBoolean(ATTR_COMPLETED, isCompleted)
        .withInt(ATTR_TYPE, type)
        .withString(ATTR_CONTEXT, context);
    return item;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("userId", userId)
        .append("taskId", taskId)
        .append("type", type)
        .append("title", title)
        .append("isCompleted", isCompleted)
        .append("context", context)
        .toString();
  }
}
