package org.example.domain;

import org.example.domain.password.PasswordHashing;
import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

public class Authentication implements IAuthentication
{
    private IPersistenceHandler persistenceHandler = new PersistenceHandler();

    /**
     * tries to login, it succeed if UserEntity gets returned.
     * @return a object of userEntity
     */
    public UserEntity login(String username, String password)
    {
        try {

            //get password salt from database
            String passwordSalt = persistenceHandler.user().getPasswordSaltFromUsername(username);

            if (password == null) return null;

            var hashedPassword = PasswordHashing.sha256(password, passwordSalt);
            return persistenceHandler.user().getUserByLoginInformation(username, hashedPassword);

        } catch (Exception e) {
            return null;
        }
    }

    public Boolean logout(User user)
    {

        return false;
    }





}
