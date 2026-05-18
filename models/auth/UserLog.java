package models;

import java.io.Serializable;
import java.util.Date;

public class UserLog implements Serializable {
    private final Date timestamp;
    private final String actorId;
    private final String action;

    public UserLog(String actorId, String action) {
        this.timestamp = new Date();
        this.actorId = actorId;
        this.action = action;
    }

    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    public String getActorId() {
        return actorId;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + actorId + " -> " + action;
    }
}
