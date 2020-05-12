package org.example.persistence.dataAccess;



public class PersistenceHandler
{

    public PersistenceUser user() {
        return new PersistenceUser();
    }


    public PersistenceProgram program() {
        return new PersistenceProgram();
    }

    public PersistenceCompany company()
    {
        return new PersistenceCompany();
    }


}
