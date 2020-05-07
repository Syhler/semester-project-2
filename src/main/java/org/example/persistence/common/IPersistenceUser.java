package org.example.persistence.common;


import org.example.persistence.entities.CompanyEntity;
import org.example.persistence.entities.UserEntity;

import java.util.List;

public interface IPersistenceUser
{
    long createUser(UserEntity userEntity,String encryptedPassword, String passwordSalt);
    UserEntity getUserById(long id);
    boolean updateUser(UserEntity userEntity, String encryptedPassword, String passwordSalt);
    boolean updateUser(UserEntity userEntity);
    boolean deleteUser(UserEntity userEntity);
    List<UserEntity> getUserByRole(int roleId); //idk?
    List<UserEntity> getUserByCompany(CompanyEntity companyEntity);
    List<UserEntity> getAllUsers();
    List<UserEntity> getAllDeletedUsers();
    UserEntity getUserByLoginInformation(String username, String password);
    String getPasswordSaltFromUsername(String username);
}
