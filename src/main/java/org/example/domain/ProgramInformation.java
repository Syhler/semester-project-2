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

    public ProgramInformation(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ProgramInformation() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
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
