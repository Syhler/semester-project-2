package org.example.domain;

public class ProgramInformation
{
    private long id;
    private String title;
    private String description;
    private Language language;

    public ProgramInformation(long id, String title, String description, Language language) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public Language getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }
}
