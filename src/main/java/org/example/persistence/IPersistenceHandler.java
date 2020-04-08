package org.example.persistence;

public interface IPersistenceHandler
{
    IPersistenceUser user();
    IPersistenceCredit credit();
    IPersistenceProgram program();
    IPersistenceCompany company();
}
