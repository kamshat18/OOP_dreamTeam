package models;

import interfaces.Researcher;

import java.util.ArrayList;
import java.util.Calendar;
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
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(paper.getPublicationDate());
            int publicationYear = calendar.get(Calendar.YEAR);
            if (publicationYear == year) {
                result.add(paper);
            }
        }
        return result;
    }

    public List<ResearchPaper> sortPapers(List<ResearchPaper> papers, Comparator<ResearchPaper> comparator) {
        if (papers == null) {
            return new ArrayList<>();
        }
        List<ResearchPaper> result = new ArrayList<>();
        for (ResearchPaper paper : papers) {
            if (paper != null) {
                result.add(paper);
            }
        }
        if (comparator != null) {
            result.sort(comparator);
        }
        return result;
    }

    public void printPapers(List<ResearchPaper> papers, Comparator<ResearchPaper> comparator) {
        for (ResearchPaper paper : sortPapers(papers, comparator)) {
            System.out.println(paper.getCitation(enums.Format.PLAIN_TEXT));
        }
    }

    public List<ResearchPaper> allResearchPapers(List<Researcher> researchers) {
        List<ResearchPaper> result = new ArrayList<>();
        if (researchers == null) {
            return result;
        }
        for (Researcher researcher : researchers) {
            if (researcher != null) {
                result.addAll(researcher.getResearchPapers());
            }
        }
        return result;
    }

    public void printAllResearchPapers(List<Researcher> researchers, Comparator<ResearchPaper> comparator) {
        printPapers(allResearchPapers(researchers), comparator);
    }

    public Researcher topCitedResearcherOfYear(List<Researcher> researchers, int year) {
        if (researchers == null) {
            return null;
        }
        Researcher best = null;
        int bestCitations = -1;
        for (Researcher researcher : researchers) {
            if (researcher == null) {
                continue;
            }
            int citations = 0;
            for (ResearchPaper paper : papersByYear(researcher.getResearchPapers(), year)) {
                citations += paper.getCitations();
            }
            if (citations > bestCitations) {
                bestCitations = citations;
                best = researcher;
            }
        }
        return best;
    }
}
