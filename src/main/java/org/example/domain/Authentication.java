package org.example.domain;

import org.example.domain.mapper.UserMapper;
import org.example.domain.password.PasswordHashing;
import org.example.persistence.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

public class Authentication
{
    private IPersistenceHandler persistenceHandler = new PersistenceHandler();

    /**
     * tries to login, it succeed if UserEntity gets returned.
     * @return a object of userEntity
     */
    public User login(String username, String password)
    {
        try {

            //get password salt from database
            String passwordSalt = persistenceHandler.user().getPasswordSaltFromUsername(username);

            if (password == null) return null;

            var hashedPassword = PasswordHashing.sha256(password, passwordSalt);
            var user = persistenceHandler.user().getUserByLoginInformation(username, hashedPassword);
            return UserMapper.map(user);

        } catch (Exception e) {
            return null;
        }
    }
}
