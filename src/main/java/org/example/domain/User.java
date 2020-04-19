package org.example.domain;

import java.util.Date;
import java.util.List;

public class User
{
    private long id;
    private String title;
    private Name name;
    private Date createdAt;
    private User createdBy;
    private String email;
    private Role role;
    private Company company;


    public User(long id, String title, Name name, Date createdAt, User createdBy, String email, Role role, Company company) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.email = email;
        this.role = role;
        this.company = company;
    }

    public Credit addCredit(String userID)
    {
        return null;
    }

    public User addProducer(String userID)
    {
        return null;
    }

    

    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Company getCompany() {
        return company;
    }

    public Name getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public User getCreatedBy() {
        return createdBy;
    }
}
