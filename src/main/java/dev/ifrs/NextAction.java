package dev.ifrs;

import java.util.HashMap;
import java.util.Map;

public class NextAction {
    private String userId; // partition key
    private int type; // sort key
    private Map<String, NextActionState> statesMap = new HashMap<>();

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

}
