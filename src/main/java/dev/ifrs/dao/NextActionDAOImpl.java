package dev.ifrs.dao;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import dev.ifrs.NextActionState;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class NextActionDAOImpl implements NextActionDAO {

    //@Inject
    //private DynamoDbClient dynamoDB;

    @Override
    public Map<String, NextActionState> listInboxTasks(String userId) {
        return Collections.singletonMap(UUID.randomUUID().toString(),
                new NextActionState("Inbox Task", Boolean.FALSE));
        // dynamoDB.batchGetItem(...)
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
