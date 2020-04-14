package org.example.domain;

import org.example.domain.password.PasswordHashing;
import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

import java.util.List;

public class User implements IUser {
    private IPersistenceHandler persistenceHandler = new PersistenceHandler();

    public Long createUser(UserEntity userEntity,String password) {
        var passwordSalt = PasswordHashing.generateSalt();
        try {

            var encryptedPassword = PasswordHashing.sha256(password,passwordSalt);
            return persistenceHandler.user().createUser(userEntity, encryptedPassword, passwordSalt);

        } catch (Exception e){
            e.printStackTrace();
        }

        return 0L;

    }

    public Boolean removeUser(UserEntity userEntity) {
        return persistenceHandler.user().deleteUser(userEntity);
    }

    public UserEntity getUserById(String id) {
        return null;
    }

    public List<UserEntity> getAllUser() {
        return null;
    }

    public Boolean updateUser(UserEntity userEntity, String password) {
        var passwordSalt = PasswordHashing.generateSalt();
        try {

            var encryptedPassword = PasswordHashing.sha256(password,passwordSalt);
            return persistenceHandler.user().updateUser(userEntity,encryptedPassword,passwordSalt);

        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


    public List<UserEntity> getUserByCompany(CompanyEntity company) {
        return null;

    }

    public List<UserEntity> getUserByRole(Role role) {

        return persistenceHandler.user().getUserByRole(role);

    }

}
