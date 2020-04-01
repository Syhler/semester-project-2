package org.example.entity;

public class UserEntity {
    private String id;
    private String name;
    private CompanyEntity company;
    private Role role;
    private String title;

    public UserEntity(String name, CompanyEntity company, Role role, String title) {
        this.name = name;
        this.company = company;
        this.role = role;
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public Role getRole() {
        return role;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
