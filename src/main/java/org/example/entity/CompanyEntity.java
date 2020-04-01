package org.example.entity;

public class CompanyEntity {
    private String id;
    private String name;

    public CompanyEntity(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
