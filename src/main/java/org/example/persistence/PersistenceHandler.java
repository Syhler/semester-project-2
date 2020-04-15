package org.example.persistence;

import java.sql.Connection;

public class PersistenceHandler implements IPersistenceHandler {



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
        return null;
    }

    @Override
    public IPersistenceCompany company()
    {
        return new PersistenceCompany();
    }
}
