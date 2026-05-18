package models.research;

import exceptions.NotResearcherException;
import interfaces.Researcher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ResearchProject implements Serializable {
    private String topic;
    private final List<ResearchPaper> publishedPapers;
    private final List<Researcher> participants;
    private Date startDate;
    private Date endDate;

    public ResearchProject(String topic, Date startDate, Date endDate) {
        this.topic = topic;
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return Collections.unmodifiableList(publishedPapers);
    }

    public List<Researcher> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null) {
            publishedPapers.add(paper);
        }
    }

    public boolean addParticipant(Researcher researcher) {
        if (researcher == null || participants.contains(researcher)) {
            return false;
        }
        participants.add(researcher);
        return true;
    }

    public boolean addParticipant(Object candidate) throws NotResearcherException {
        if (!(candidate instanceof Researcher)) {
            throw new NotResearcherException("Participant must implement Researcher.");
        }
        Researcher researcher = (Researcher) candidate;
        return addParticipant(researcher);
    }
}
