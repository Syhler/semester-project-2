package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;

import java.sql.Connection;
import java.util.List;

public class PersistenceUser extends BasePersistence implements IPersistenceUser {


    private Connection connection;

    public PersistenceUser()
    {
        connection = initializeDatabase();
    }

    @Override
    public boolean createUser(UserEntity userEntity) {
        return false;
    }

    @Override
    public UserEntity getUserById(String id) {
        return null;
    }

    @Override
    public boolean updateUser(UserEntity userEntity) {
        return false;
    }

    @Override
    public boolean deleteUser(UserEntity userEntity) {
        return false;
    }

    @Override
    public List<UserEntity> getUserByRole(Role role) {
        return null;
    }

    @Override
    public List<UserEntity> getUserByCompany(CompanyEntity companyEntity) {
        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return null;
    }

    @Override
    public UserEntity getUserByLoginInformation(String username, String password)
    {

    }
}
