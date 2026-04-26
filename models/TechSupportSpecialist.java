package models;

import enums.RequestStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TechSupportSpecialist extends Employee {
    private final List<Request> requests;

    public TechSupportSpecialist(String id, String fullName, String email, String password, String language,
                                 double salary, Date hireDate, String employeeId) {
        super(id, fullName, email, password, language, salary, hireDate, employeeId);
        this.requests = new ArrayList<>();
    }

    public List<Request> viewRequests() {
        return Collections.unmodifiableList(requests);
    }

    public void acceptRequest(Request request) {
        updateRequestStatus(request, RequestStatus.ACCEPTED);
    }

    public void rejectRequest(Request request, String reason) {
        updateRequestStatus(request, RequestStatus.REJECTED);
    }

    public void markRequestDone(Request request) {
        updateRequestStatus(request, RequestStatus.DONE);
    }

    public void updateRequestStatus(Request request, RequestStatus status) {
        if (request == null || status == null) {
            return;
        }
        if (!requests.contains(request)) {
            requests.add(request);
        }
        request.updateStatus(status);
    }
}
