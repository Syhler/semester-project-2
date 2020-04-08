package org.example.entity;

import java.util.Date;

public class UserEntity {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String email;
    private CompanyEntity company;
    private String companyName;
    private Role role;
    private String title;
    private UserEntity createdBy;
    private Date createdAt;
    private String createdByName;



    public UserEntity(String title, String firstName, String middleName, String lastName,
                      Date createdAt,String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.company = company;
        this.role = role;
        this.title = title;
        this.fullName = firstName+" "+middleName+" "+lastName;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }



    public void setRole(int role) {
        this.role = Role.getRoleById(role);
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedByName(String name){
        this.createdByName = name;
    }

    public String getCreatedByName(){
        return createdByName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
