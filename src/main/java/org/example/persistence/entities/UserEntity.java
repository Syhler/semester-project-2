package org.example.persistence.entities;

import java.util.Date;
import java.util.List;

public class UserEntity {
    private long id;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private CompanyEntity companyEntity;
    private int role;
    private UserEntity createdBy;
    private Date createdAt;


    public UserEntity(long id, String title, String firstName, String middleName, String lastName,
                      String email, CompanyEntity companyEntity, int role,
                      UserEntity createdBy, Date createdAt) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.companyEntity = companyEntity;
        this.role = role;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public UserEntity(long id, String firstName, String middleName, String lastName, String email, String title) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
    }


    public UserEntity(long id, String title, String firstName, String middleName, String lastName, java.sql.Date createdAt, String email, int role)
    {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.createdAt = createdAt;
        this.role = role;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }
}
