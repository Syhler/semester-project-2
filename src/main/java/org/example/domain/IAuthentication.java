package org.example.domain;
import org.example.entity.*;
public interface IAuthentication extends  IDomainHandler {

    public UserEntity login(String userName, String passWord);
    public Boolean logOut(User user);
}
