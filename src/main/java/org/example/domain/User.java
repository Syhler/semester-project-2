package org.example.domain;

import java.util.List;

public class User implements IUser {
    private IPersistenceHandler persistenceHandler;

    public Boolean createUser(UserEntity userEntity){
        return false;
    }
    public Boolean removeUser(UserEntity userEntity){
        return false;
    }
    public UserEntity getUserById(String id){
        return null;
    }
    public List<UserEntity> getAllUser(){
        return null;
    }
    public Boolean updateUser(UserEntity userEntity){
        return false
    }
    public List<CompanyEntity> getUserByCompany(Company company){
        return null;
    }
    public List<UserEntity> getUserByRole(Role role){
        return null;
    }


}
