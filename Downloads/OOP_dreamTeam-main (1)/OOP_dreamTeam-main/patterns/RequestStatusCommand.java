package patterns;

import enums.RequestStatus;
import models.Request;
import models.TechSupportSpecialist;

public class RequestStatusCommand implements Command {
    private final TechSupportSpecialist specialist;
    private final Request request;
    private final RequestStatus status;
    private final String reason;

    public RequestStatusCommand(TechSupportSpecialist specialist, Request request, RequestStatus status) {
        this(specialist, request, status, null);
    }

    public RequestStatusCommand(TechSupportSpecialist specialist, Request request, RequestStatus status, String reason) {
        this.specialist = specialist;
        this.request = request;
        this.status = status;
        this.reason = reason;
    }

    @Override
    public void execute() {
        if (specialist == null || request == null || status == null) {
            return;
        }
        if (status == RequestStatus.ACCEPTED) {
            specialist.acceptRequest(request);
        } else if (status == RequestStatus.REJECTED) {
            specialist.rejectRequest(request, reason);
        } else if (status == RequestStatus.DONE) {
            specialist.markRequestDone(request);
        } else {
            specialist.updateRequestStatus(request, status);
        }
    }
}
