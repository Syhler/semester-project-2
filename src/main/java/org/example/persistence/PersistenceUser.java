package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name " +
                    "from \"user\", company where \"user\".id = ?  and company.id = \"user\".company");
            preparedStatement.setString(1, id);

            var resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
            {
                return null;
            }

            return createUserEntityFromResultSet(resultSet);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name " +
                    "from \"user\", company where email = ? and password = ?  and company.id = \"user\".company");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
            {
                return null;
            }


            return createUserEntityFromResultSet(resultSet);



            //return null;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private UserEntity createUserEntityFromResultSet(ResultSet resultSet) throws SQLException {
        var user = new UserEntity(
                        resultSet.getString("title"),
                        resultSet.getString("firstName"),
                        resultSet.getString("middleName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("createdAt"),
                        resultSet.getString("email"));

        var company = new CompanyEntity(resultSet.getString("name"));
        company.setId(resultSet.getString(9)); //9 equal company id
        user.setCompany(company);
        user.setRole(resultSet.getInt("role"));
        user.setId(resultSet.getString(1)); // 1 equal user id

        if (resultSet.getString("createdBy") != null)
        {
            user.setCreatedBy(getUserById(resultSet.getString("createdBy")));
        }

        return user;

    }
}
