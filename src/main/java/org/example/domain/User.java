package org.example.domain;

import org.example.domain.mapper.UserMapper;
import org.example.domain.password.PasswordHashing;
import org.example.persistence.PersistenceHandler;
import org.example.presentation.utilities.UsermanagementUtilities;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class User
{
    private PersistenceHandler persistenceHandler = new PersistenceHandler();
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

    public User(String title, String firstName, String middleName, String lastName, Date createdAt,
                String email, Role role, User createdBy)
    {
        this.title = title;
        this.name = new Name(firstName, Collections.singletonList(middleName), lastName);
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.email = email;
        this.role = role;
    }

    public User(String title, String firstName, String middleName, String lastName, Date createdAt,
                String email, Role role, User createdBy, Company company)
    {
        this.title = title;
        this.name = new Name(firstName, Collections.singletonList(middleName), lastName);
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.email = email;
        this.role = role;
        this.company = company;
    }

    public User(long id, String title, String firstName, String middleName, String lastName, Date createdAt,
                String email, Role role, User createdBy, Company company)
    {
        this.id = id;
        this.title = title;
        this.name = new Name(firstName, Collections.singletonList(middleName), lastName);
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName()
    {
        if (name.getMiddleNames().size() > 0)
        {
            return name.getFirstName() + " " + name.getFirstMiddleName() + " " + name.getLastName();
        }

        return name.getFirstName() + " " + name.getLastName();
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

    public boolean update(String passwordText) {
        var mapped = UserMapper.map(this);

        var salt = PasswordHashing.generateSalt();
        String encryptedPassword = null;
        try {
            encryptedPassword = PasswordHashing.sha256(passwordText, salt);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return persistenceHandler.user().updateUser(mapped, encryptedPassword, salt);
    }

    public boolean update() {
        var mapped = UserMapper.map(this);

        return persistenceHandler.user().updateUser(mapped);
    }

    public boolean delete()
    {
        var mapped = UserMapper.map(this);
        return persistenceHandler.user().deleteUser(mapped);
    }
}
