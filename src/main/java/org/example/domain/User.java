package org.example.domain;

import java.util.List;

public class User implements IUser {
    private IPersistenceHandler persistenceHandler;

    public Boolean createUser(UserEntity userEntity){}
    public Boolean removeUser(UserEntity userEntity){}
    public UserEntity getUserById(String id){}
    public List<UserEntity> getAllUser(){}
    public Boolean updateUser(UserEntity userEntity){}
    public List<UserEntity> getUserByCompany(Company company){}
    public List<UserEntity> getUserByRole(Role role){}


}
