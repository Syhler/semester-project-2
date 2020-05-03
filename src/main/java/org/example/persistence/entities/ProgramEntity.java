package org.example.persistence.entities;

import java.util.ArrayList;
import java.util.List;

public class ProgramEntity {

    private long id;
    private ProgramInformationEntity programInformation;
    private List<UserEntity> producer;
    private List<CreditEntity> credits;
    private CompanyEntity companyEntity;


    public ProgramEntity(long id, ProgramInformationEntity programInformation, List<UserEntity> producer, List<CreditEntity> credits, CompanyEntity companyEntity) {
        this.id = id;
        this.programInformation = programInformation;
        this.producer = producer;
        this.credits = credits;
        this.companyEntity = companyEntity;
    }

    public ProgramEntity(long program_id, String title, String description, CompanyEntity companyEntity, ArrayList<UserEntity> producerForProgram, ArrayList<CreditEntity> creditUser) {
        id = program_id;
        programInformation = new ProgramInformationEntity(title, description);
        this.companyEntity = companyEntity;
        producer = producerForProgram;
        credits = creditUser;
    }

    public ProgramEntity(long program_id, String title) {
        id = program_id;
        programInformation = new ProgramInformationEntity(title);
    }



    public ProgramEntity(long programId, ProgramInformationEntity programInformation)
    {
        this.id = programId;
        this.programInformation = programInformation;
    }

    public void setProgramInformation(ProgramInformationEntity programInformation) {
        this.programInformation = programInformation;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public void setCredits(List<CreditEntity> credits) {
        this.credits = credits;
    }

    public void setProducer(List<UserEntity> producer) {
        this.producer = producer;
    }

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CreditEntity> getCredits() {
        return credits;
    }

    public List<UserEntity> getProducer() {
        return producer;
    }

    public ProgramInformationEntity getProgramInformation() {
        return programInformation;
    }

}

