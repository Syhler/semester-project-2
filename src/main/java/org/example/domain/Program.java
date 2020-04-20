package org.example.domain;

import org.example.domain.io.Export;
import org.example.domain.mapper.CreditMapper;
import org.example.domain.mapper.ProgramMapper;
import org.example.domain.mapper.UserMapper;
import org.example.persistence.PersistenceHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Program
{

    private PersistenceHandler persistenceHandler = new PersistenceHandler();
    private long id;
    private ProgramInformation programInformation;
    private List<Credit> credits;
    private List<User> producers;
    private Company company;


    public Program(long id, ProgramInformation programInformation, List<Credit> credits,
                   List<User> producers, Company company) {
        this.id = id;
        this.programInformation = programInformation;
        this.credits = credits;
        this.producers = producers;
        this.company = company;
    }

    public Program(String title, String description, Company company)
    {
        this.producers = new ArrayList<>();
        this.credits = new ArrayList<>();
        this.programInformation = new ProgramInformation(title, description);
        this.company = company;
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

    public ProgramInformation getProgramInformation()
    {
        if (programInformation == null)
        {
            programInformation = new ProgramInformation();
        }
        return programInformation;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public void setProducers(List<User> producers) {
        this.producers = producers;
    }

    public boolean deleteCredit(Credit creditToDelete) {
        var mapped = CreditMapper.map(creditToDelete);

        credits.removeIf(credit -> credit.getUser().getId() == creditToDelete.getUser().getId());

        return persistenceHandler.program().removeCreditFromProgram(mapped);
    }

    public boolean deleteProducer(User producerToDelete) {
        var mapped = UserMapper.map(producerToDelete);

        producers.removeIf(producer -> producer.getId() == producerToDelete.getId());

        return persistenceHandler.program().removeUserFromProgram(mapped, id);

    }

    public File export(String filePath)
    {
        return Export.program(this, filePath);
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




}
