package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;

import java.util.List;

public interface IPersistenceUser
{
    Long createUser(UserEntity userEntity,String encryptedPassword, String passwordSalt);
    UserEntity getUserById(Long id);
    boolean updateUser(UserEntity userEntity, String encryptedPassword, String passwordSalt);
    boolean deleteUser(UserEntity userEntity);
    List<UserEntity> getUserByRole(Role role);
    List<UserEntity> getUserByCompany(CompanyEntity companyEntity);
    List<UserEntity> getAllUsers();
    UserEntity getUserByLoginInformation(String username, String password);
    String getPasswordSaltFromUsername(String username);
}
