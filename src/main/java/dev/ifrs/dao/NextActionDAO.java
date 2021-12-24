package dev.ifrs.dao;

import java.util.List;
import java.util.Map;

import dev.ifrs.NextAction;
import dev.ifrs.NextActionState;

public interface NextActionDAO {
    
    public List<NextAction> listInboxTasks(final String userId);

    public Map<String, NextActionState> listNextActionTasks(final String userId);

    public void addTask(final String userId, final int type, final String title);

    public int getTask(final String userId);

    public void completeTask(final String userId, final int type, final String taskId);

    public void renameTask(final String userId, final int type, final String newTitle);

    public void deleteTask(final String userId, final int type, final String taskId);
}
