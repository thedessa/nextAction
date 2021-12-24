package dev.ifrs;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@RegisterForReflection
public class NextAction {
    private String userId; // partition key
    private int type; // sort key
    private Map<String, NextActionState> statesMap = new HashMap<>();

    public NextAction() {
    
    }

    public NextAction(final Item item) {
        this.userId = item.getString("userId");
        this.type = item.getNumber("type").intValue();
      }

    public NextAction(final String userId,
    final ENextActionType type,
    final Map<String, NextActionState> statesMap) {
        this.userId = userId;
        this.type = type.getIdentifier();
        this.setStatesMap(statesMap);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, NextActionState> getStatesMap() {
        return statesMap;
    }

    public void setStatesMap(Map<String, NextActionState> statesMap) {
        this.statesMap = statesMap;
    }

    public static NextAction fromItem(Map<String, AttributeValue> attributes){
        NextAction task = new NextAction();
        task.setUserId(attributes.get("userId").s());
        task.setType(Integer.parseInt(attributes.get("type").n()));
        return task;
    }

}
