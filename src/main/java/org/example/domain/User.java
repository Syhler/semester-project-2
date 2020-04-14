package org.example.domain;

import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

import java.util.List;

public class User implements IUser {
    private IPersistenceHandler persistenceHandler = new PersistenceHandler();

    public Long createUser(UserEntity userEntity,String password) {
        // Hashing of password must happen here in domain
        return persistenceHandler.user().createUser(userEntity, password);
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
        return persistenceHandler.user().updateUser(userEntity,password);
    }


    public List<UserEntity> getUserByCompany(CompanyEntity company) {
        return null;

    }

    public List<UserEntity> getUserByRole(Role role) {

        return persistenceHandler.user().getUserByRole(role);

    }

}
