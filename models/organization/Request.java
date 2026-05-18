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
    public Request(String requestId, String description, User requester) {
        this.requestId = requestId;
        this.description = description;
        this.requester = requester;
        this.status = RequestStatus.PENDING;
        this.createdDate = new Date();
    }
    public RequestStatus getStatus() {
        return status;
    }
    public void updateStatus(RequestStatus status) {
        this.status = status;
        if (status == RequestStatus.DONE || status == RequestStatus.REJECTED)  this.resolvedDate = new Date();
    }
    public String getRequestInfo() {
        String s = "models.Request #" + requestId + " by " + (requester == null ? "anonym" : requester.toString()) + " created at " + createdDate;
        if (resolvedDate != null) s += ", resolved at " + resolvedDate;
        s += ": " + description;
        return s;
    }
}