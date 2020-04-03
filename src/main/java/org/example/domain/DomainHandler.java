package org.example.domain;

public class DomainHandler implements IDomainHandler {

    public IAuthentication authentication(){
        return new Authentication();
    }
    public IProgram program(){
        return new Program();
    }
    public ICredit credit(){
        return null;
    }
    public IUser user(){
        return null;
    }
}
