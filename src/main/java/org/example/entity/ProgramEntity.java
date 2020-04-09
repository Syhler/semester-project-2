package org.example.entity;


import java.util.Arrays;
import java.util.List;

public class ProgramEntity {
    private String id;
    private String name;
    private String description;
    private List<CompanyEntity> companies;
    private List<UserEntity> producer;
    private List<CreditEntity> credits;

    public ProgramEntity(String name, String description, List<CompanyEntity> companies, List<UserEntity> producer, List<CreditEntity> credits) {
        this.name = name;
        this.description = description;
        this.companies = companies;
        this.name = name;
        this.description = description;
        this.producer = producer;
        this.credits = credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompanies(List<CompanyEntity> companies) {
        this.companies = companies;
    }

    public void setProducer(List<UserEntity> producer) {
        this.producer = producer;
    }

    public void setCredits(List<CreditEntity> credits) {
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<CompanyEntity> getCompanies() {
        return companies;
    }

    public List<UserEntity> getProducer() {
        return producer;
    }

    public List<CreditEntity> getCredits() {
        return credits;
    }

    public void addCredit(CreditEntity credit) {
        credits.add(credit);
    }

    public void removeCredit(CreditEntity credit) {
        credits.remove(credit);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name + " Description: " + description + " Company: " + companies + " Producer: " + Arrays.asList(producer) + " Credits: " + Arrays.asList(credits);
    }

}
