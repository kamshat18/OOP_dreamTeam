package OOP_dreamTeam.models;

import OOP_dreamTeam.interfaces.Researcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchAnalytics {
    public List<Researcher> topCitedResearchers(List<Researcher> researchers, int limit) {
        if (researchers == null || limit <= 0) {
            return new ArrayList<>();
        }
        return researchers.stream()
                .filter(r -> r != null)
                .sorted(Comparator.comparingInt(Researcher::calculateHIndex).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<ResearchPaper> papersByYear(List<ResearchPaper> papers, int year) {
        if (papers == null) {
            return new ArrayList<>();
        }
        List<ResearchPaper> result = new ArrayList<>();
        for (ResearchPaper paper : papers) {
            if (paper == null || paper.getPublicationDate() == null) {
                continue;
            }
            @SuppressWarnings("deprecation")
            int publicationYear = paper.getPublicationDate().getYear() + 1900;
            if (publicationYear == year) {
                result.add(paper);
            }
        }
        return result;
    }
}
