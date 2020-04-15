package org.example.entity;

public class CompanyEntity {

    private long id;
    private String name;

    public CompanyEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CompanyEntity(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
