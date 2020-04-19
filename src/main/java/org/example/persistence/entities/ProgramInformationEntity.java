package org.example.persistence.entities;


public class ProgramInformationEntity
{
    private long id;
    private String title;
    private String description;
    private int language;

    public ProgramInformationEntity(long id, String title, String description, int language)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.language = language;
    }

    public ProgramInformationEntity(String title, String description) {
        this.title = title;
        this.description = description;
        language = 1;
    }

    public ProgramInformationEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getLanguage() {
        return language;
    }
}
