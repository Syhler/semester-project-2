package org.example.domain.buisnessComponents;

import org.example.domain.mapper.CompanyMapper;
import org.example.persistence.dataAccess.PersistenceHandler;

public class Company
{
    private final PersistenceHandler persistenceHandler = new PersistenceHandler();
    private String name;
    private long id;

    public Company(long id,String name) {
        this.name = name;
        this.id = id;
    }

    public Company(String name) {
        this.name = name;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean unDeleteCompany(){
        var mapped = CompanyMapper.map(this);
        return persistenceHandler.company().unDeleteCompany(mapped);
    }
}
