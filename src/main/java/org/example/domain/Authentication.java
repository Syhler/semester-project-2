package org.example.domain;

import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;

public class Authentication implements IAuthentication
{
    private IPersistenceHandler persistenceHandler;

    public UserEntity login(String username, String password){

        return null;
    }

    public Boolean logout(User user){
        return false;
    }



}
