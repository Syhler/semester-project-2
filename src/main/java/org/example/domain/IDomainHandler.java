package org.example.domain;


public interface IDomainHandler
{
    public IAuthentication authentication();
    public IProgram program();
    public ICredit credit();
    public IUser user();
}
