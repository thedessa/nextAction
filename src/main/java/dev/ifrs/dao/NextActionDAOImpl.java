package dev.ifrs.dao;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import dev.ifrs.NextActionState;

public class NextActionDAOImpl implements NextActionDAO {

    @Override
    public Map<String, NextActionState> listInboxTasks(String userId) {
        return Collections.singletonMap(UUID.randomUUID().toString(),
                new NextActionState("Inbox Task", Boolean.FALSE));
    }

    @Override
    public Map<String, NextActionState> listNextActionTasks(String userId) {
        return Collections.singletonMap(UUID.randomUUID().toString(),
                new NextActionState("Next Action Task", Boolean.TRUE));
    }

    @Override
    public void addTask(String userId, final int type, String title) {
        // Add task to map
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
