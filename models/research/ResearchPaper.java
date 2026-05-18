package OOP_dreamTeam.models;

import enums.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ResearchPaper implements Comparable<ResearchPaper> {
    private String title;
    private List<String> authors;
    private String journal;
    private int citations;
    private int pages;
    private Date publicationDate;
    private String doi;

    public ResearchPaper(String title, List<String> authors, String journal, int citations, int pages, Date publicationDate, String doi) {
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.journal = journal;
        this.citations = citations;
        this.pages = pages;
        this.publicationDate = publicationDate;
        this.doi = doi;
    }

    public int getCitations() {
        return citations;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public List<String> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public String getCitation(Format format) {
        if (format == Format.BIBTEX) {
            return "@article{" + doi + ", title={" + title + "}, author={" + String.join(" and ", authors) + "}, journal={" + journal + "}}";
        }
        return String.join(", ", authors) + ". " + title + ". " + journal + ". DOI: " + doi;
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(this.citations, other.citations);
    }
}
