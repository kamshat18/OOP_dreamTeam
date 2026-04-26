package models;

import enums.RequestStatus;

import java.util.Date;

public class Request {
    private String requestId;
    private String description;
    private User requester;
    private RequestStatus status;
    private Date createdDate;
    private Date resolvedDate;

    public void updateStatus(RequestStatus status) {
        this.status = status;
    }
    public String getRequestInfo() {
        String s = "models.Request #" + requestId + " by " + (requester == null ? "anonym" : requester.toString()) + " created at " + createdDate;
        if (resolvedDate != null) s += ", resolved at " + resolvedDate;
        s += ": " + description;
        return s;
    }
}