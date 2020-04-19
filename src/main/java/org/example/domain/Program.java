package org.example.domain;

import org.example.domain.mapper.ProgramMapper;
import org.example.persistence.PersistenceHandler;

import java.util.List;

public class Program
{

    private PersistenceHandler persistenceHandler = new PersistenceHandler();
    private long id;
    private ProgramInformation programInformation;
    private List<Credit> credits;
    private List<User> producers;
    private Company company;


    public Program(long id, ProgramInformation programInformation, List<Credit> credits, List<User> producers, Company company) {
        this.id = id;
        this.programInformation = programInformation;
        this.credits = credits;
        this.producers = producers;
        this.company = company;
    }

    public boolean update()
    {
        var mapped = ProgramMapper.map(this);
        return persistenceHandler.program().updateProgram(mapped);
    }

    public boolean delete()
    {
        return persistenceHandler.program().deleteProgram(id);
    }

    public long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public List<User> getProducers() {
        return producers;
    }

    public ProgramInformation getProgramInformation() {
        return programInformation;
    }
}
