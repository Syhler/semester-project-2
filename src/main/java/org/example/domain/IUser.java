package org.example.domain;
import org.example.entity.*;
import java.util.List;

public interface IUser {
   public Long createUser(UserEntity userEntity,String password) throws Exception;

   public Boolean removeUser(UserEntity userEntity);

   public UserEntity getUserById(String id);

   public List<UserEntity> getAllUser();

   public Boolean updateUser(UserEntity userEntity, String password);

   public List<UserEntity> getUserByCompany(CompanyEntity companyEntity);

   public List<UserEntity> getUserByRole(Role role);
}
