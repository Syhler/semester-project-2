package org.example.persistence.entities;

public class CompanyEntity
{

    private long id;
    private String name;

    public CompanyEntity(long id, String name) {
        this.name = name;
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
