package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;

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

    /**
     * Search the database for a user based on the given ID
     * @param id of the user you want to find
     * @return a UserEntity object if a user has been found
     */
    @Override
    public UserEntity getUserById(String id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name " +
                    "from \"user\", company where \"user\".id = ?  and company.id = \"user\".company");
            preparedStatement.setString(1, id);

            var resultSet = preparedStatement.executeQuery();

            //checks if the resultSet contains any rows
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

    /**
     * searching in the database for a user based on the given username and hashedPassword
     * @return a UserEntity object if a user has been found
     */
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

            //checks if the resultSet contains any rows
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

    /**
     * searching in the database for a password salt based on the given username
     * @return password salt if found
     */
    @Override
    public String getPasswordSaltFromUsername(String username) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passwordSalt from \"user\" where email = ?");
            preparedStatement.setString(1, username);

            var resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
            {
                return null;
            }

            return resultSet.getString("passwordSalt");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Generates a UserEntity from the given resultSet
     * @param resultSet the result set from a executed preparedStatement
     * @return A Object of UserEntity
     * @throws SQLException if the given resultSet doesn't have the required column.
     */
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

        //Makes a recursion call. It will loop through until an user isn't createdBy is null
        if (resultSet.getString("createdBy") != null)
        {
            user.setCreatedBy(getUserById(resultSet.getString("createdBy")));
        }

        return user;

    }
}
