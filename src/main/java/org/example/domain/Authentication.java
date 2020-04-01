package org.example.domain;

import org.example.domain.password.PasswordHashing;
import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;

public class Authentication implements IAuthentication
{
    private IPersistenceHandler persistenceHandler;

    public UserEntity login(String username, String password)
    {
        try {
            var hashedPassword = PasswordHashing.sha256(password);
        } catch (Exception e) {
            return null;
        }

        persistenceHandler.user().

        return null;
    }

    public Boolean logout(User user)
    {

        return false;
    }





}
