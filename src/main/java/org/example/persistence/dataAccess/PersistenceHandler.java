package org.example.persistence.dataAccess;


import org.example.persistence.common.*;

public class PersistenceHandler implements IPersistenceHandler
{

    @Override
    public IPersistenceUser user() {
        return new PersistenceUser();
    }

    @Override
    public IPersistenceCredit credit() {
        return null;
    }

    @Override
    public IPersistenceProgram program() {
        return new PersistenceProgram();
    }

    @Override
    public IPersistenceCompany company()
    {
        return new PersistenceCompany();
    }
}
