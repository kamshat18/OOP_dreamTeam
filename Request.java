import model.Users.User;

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
        String s = "Request #" + requestId + " by " + requester.toString() + " created at " + createdDate;
        if (resolvedDate != null) s += ", resolved at " + resolvedDate;
        s += ": " + description;
        return s;
    }
}