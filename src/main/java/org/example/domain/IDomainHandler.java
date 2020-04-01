package org.example.domain;
import org.example.persistence.*;
public interface IDomainHandler {
public IAuthentication authentication();
public IPersistenceProgram program();
public IPersistenceCredit credit();
public IUser user();
}
