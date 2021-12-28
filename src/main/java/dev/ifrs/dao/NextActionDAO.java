package dev.ifrs.dao;

import java.util.List;

import dev.ifrs.model.NextAction;

public interface NextActionDAO {

  List<NextAction> listTasks(final String userId);

  void addTask(final String userId, final int type, final String title);

  void completeTask(final String userId, final String taskId);

  void renameTask(final String userId, final String taskId, final String newTitle);

  void updateContext(final String userId, final String taskId, final String newContext);

  void deleteTask(final String userId, final String taskId);
}
