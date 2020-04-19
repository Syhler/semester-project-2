package org.example.domain;

import org.example.domain.mapper.CompanyMapper;
import org.example.persistence.PersistenceHandler;

public class Company
{
    private PersistenceHandler persistenceHandler = new PersistenceHandler();
    private String name;
    private long id;

    public Company(long id,String name) {
        this.name = name;
        this.id = id;
    }

    public boolean update()
    {
        var mapped = CompanyMapper.map(this);
        return persistenceHandler.company().updateCompany(mapped);
    }

    public boolean delete()
    {
        var mapped = CompanyMapper.map(this);
        return persistenceHandler.company().deleteCompany(mapped);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
