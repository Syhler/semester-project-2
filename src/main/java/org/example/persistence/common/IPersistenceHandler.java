package org.example.persistence.common;

public interface IPersistenceHandler
{
    IPersistenceUser user();
    IPersistenceCredit credit();
    IPersistenceProgram program();
    IPersistenceCompany company();
}
