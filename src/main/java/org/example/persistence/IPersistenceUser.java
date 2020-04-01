package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;

import java.util.List;

public interface IPersistenceUser
{
    boolean createUser(UserEntity userEntity);
    UserEntity getUserById(String id);
    boolean updateUser(UserEntity userEntity);
    boolean deleteUser(UserEntity userEntity);
    List<UserEntity> getUserByRole(Role role);
    List<UserEntity> getUserByCompany(CompanyEntity companyEntity);
    List<UserEntity> getAllUsers();
}
