package org.example.domain;

public class DomainHandler implements IDomainHandler {

    public IAuthentication authentication(){
        return new Authentication();
    }
    public IProgram program(){
        return null;
    }
    public ICredit credit(){
        return null;
    }
    public IUser user(){
        return new User();
    }
    public ICompany company()
    {
        return new Company();
    }
}
