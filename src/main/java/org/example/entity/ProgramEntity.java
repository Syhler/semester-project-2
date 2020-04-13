package org.example.entity;


import java.util.Arrays;
import java.util.List;

public class ProgramEntity {
    private long id;
    private long programinformationId;
    private String name;
    private String description;
    private CompanyEntity company;
    private List<UserEntity> producer;
    private List<CreditEntity> credits;

    public ProgramEntity(long id, String name, String description, CompanyEntity company, List<UserEntity> producer,
                         List<CreditEntity> credits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.company = company;
        this.producer = producer;
        this.credits = credits;
    }

    public ProgramEntity(String name, String description, CompanyEntity company, List<UserEntity> producer,
                         List<CreditEntity> credits) {

        this.name = name;
        this.description = description;
        this.company = company;
        this.producer = producer;
        this.credits = credits;
    }

    public ProgramEntity(long id, String name) {
        this.id = id;
        this.name = name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
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

    public CompanyEntity getCompany() {
        return company;
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

    public long getPrograminformationId() {
        return programinformationId;
    }

    public void setPrograminformationId(long programinformationId) {
        this.programinformationId = programinformationId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name + " Description: " + description + " Company: " + company + " Producer: " + Arrays.asList(producer) + " Credits: " + Arrays.asList(credits);
    }

}
