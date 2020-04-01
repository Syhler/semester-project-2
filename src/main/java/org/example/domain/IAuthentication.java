package org.example.domain;
import org.example.entity.*;
public interface IAuthentication {

    public UserEntity login(String username, String password);
    public Boolean logout(User user);
}
