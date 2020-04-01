package org.example.domain;
import org.example.entity.*;
public interface IAuthentication extends  IDomainHandler {

    public UserEntity login(String username, String password);
    public Boolean logOut(User user);
}
