package org.example.domain.mapper;

import org.example.domain.buisnessComponents.Name;
import org.example.domain.buisnessComponents.Role;
import org.example.domain.buisnessComponents.User;
import org.example.persistence.entities.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMapper
{

    public static List<User> map(List<UserEntity> userEntities)
    {
        if (userEntities == null) return null;

        var temp = new ArrayList<User>();

        for (var userEntity: userEntities) {
            temp.add(map(userEntity));
        }

        return temp;
    }

    public static List<UserEntity> mapUserToEntity(List<User> users)
    {
        if (users == null) return null;

        var temp = new ArrayList<UserEntity>();

        for (var user : users) {
            temp.add(map(user));
        }

        return temp;
    }

    public static User map(UserEntity userEntity)
    {
        if (userEntity == null)
        {
            return null;
        }

        return new User(
                userEntity.getId(),
                userEntity.getTitle(),
                new Name(userEntity.getFirstName(),
                        Collections.singletonList(userEntity.getMiddleName()),
                        userEntity.getLastName()),
                userEntity.getCreatedAt(),
                map(userEntity.getCreatedBy()),
                userEntity.getEmail(),
                Role.getRoleById(userEntity.getRole()),
                CompanyMapper.map(userEntity.getCompanyEntity())

        );

    }

    public static UserEntity map(User user)
    {
        if (user == null) return null;

        return new UserEntity(
                user.getId(),
                user.getTitle(),
                (user.getName() != null) ? user.getName().getFirstName() : "",
                (user.getName() != null) ? user.getName().getFirstMiddleName() : "",
                (user.getName() != null) ? user.getName().getLastName() : "",
                user.getEmail(),
                CompanyMapper.map(user.getCompany()),
                (user.getRole() != null) ? user.getRole().getValue() : 0,
                map(user.getCreatedBy()),
                user.getCreatedAt()
        );
    }
}
