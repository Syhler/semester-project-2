package org.example.persistence;

import org.example.persistence.entities.CompanyEntity;
import org.example.persistence.entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenceUser extends BasePersistence implements IPersistenceUser {


    private Connection connection;

    public PersistenceUser()
    {
        connection = initializeDatabase();
    }

    @Override
    public long createUser(UserEntity userEntity , String encryptedPassword, String passwordSalt) {
        java.util.Date utilDate = userEntity.getCreatedAt();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "Insert INTO \"user\" (title,firstname, middlename, lastname, createdby, createdat, email, password, passwordsalt, role, company)" +
                    " values (?,?,?,?,?,?,?,?,?,?,?) returning id;");
            preparedStatement.setString(1,userEntity.getTitle());
            preparedStatement.setString(2,userEntity.getFirstName());
            preparedStatement.setString(3,userEntity.getMiddleName());
            preparedStatement.setString(4,userEntity.getLastName());
            preparedStatement.setLong(5,userEntity.getCreatedBy().getId());
            preparedStatement.setDate(6,sqlDate);
            preparedStatement.setString(7,userEntity.getEmail());
            preparedStatement.setString(8,encryptedPassword);
            preparedStatement.setString(9,passwordSalt);
            preparedStatement.setInt(10,userEntity.getRole());
            preparedStatement.setLong(11,userEntity.getCompanyEntity().getId());

            var resultSet = preparedStatement.executeQuery();
            //checks if the resultSet contains any rows
            if (!resultSet.next())
            {
                return 0;
            }


                return resultSet.getLong("id");

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * Search the database for a user based on the given ID
     * @param id of the user you want to find
     * @return a UserEntity object if a user has been found
     */
    @Override
    public UserEntity getUserById(long id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id, title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name,\"user\".company  " +
                    "from \"user\", company where \"user\".id = ?  and company.id = \"user\".company");
            preparedStatement.setLong(1, id);



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
    public boolean updateUser(UserEntity userEntity, String encryptedPassword, String passwordSalt) {
        java.util.Date utilDate = userEntity.getCreatedAt();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE \"user\" SET title = ?, firstname = ?,middlename = ?," +
                    "lastname = ?,createdat = ?,email = ?,password = ?,passwordsalt = ?,role = ?,company = ?" +
                    "WHERE \"user\".id = ?;");
            preparedStatement.setString(1,userEntity.getTitle());
            preparedStatement.setString(2,userEntity.getFirstName());
            preparedStatement.setString(3,userEntity.getMiddleName());
            preparedStatement.setString(4,userEntity.getLastName());
            preparedStatement.setDate(5,sqlDate);
            preparedStatement.setString(6,userEntity.getEmail());
            preparedStatement.setString(7,encryptedPassword);
            preparedStatement.setString(8,passwordSalt);
            preparedStatement.setInt(9,userEntity.getRole());
            preparedStatement.setLong(10,userEntity.getCompanyEntity().getId());
            preparedStatement.setLong(11,userEntity.getId());

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUser(UserEntity userEntity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM \"user\" WHERE \"user\".id = ?;");
            preparedStatement.setLong(1,userEntity.getId());
            preparedStatement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<UserEntity> getUserByRole(int roleId) {
        List<UserEntity> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id, title, firstName, middleName, lastName, createdBy, createdAt, email, role,  " +
                    "company.id, company.name, \"user\".company from \"user\" " +
                    "left join company on company.id = \"user\".company where \"user\".role = ? ORDER BY \"user\".id ASC");
            preparedStatement.setInt(1, roleId);

            var resultSet = preparedStatement.executeQuery();

            //checks if the resultSet contains any rows
           while (resultSet.next()){
               users.add(createUserEntityFromResultSet(resultSet));
           }

            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserEntity> getUserByCompany(CompanyEntity companyEntity) {
        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id, title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name, \"user\".company " +
                    "from \"user\", company ORDER BY \"user\".id ASC");

            var resultSet = preparedStatement.executeQuery();

            //checks if the resultSet contains any rows
            while (resultSet.next()){
                users.add(createUserEntityFromResultSet(resultSet));
            }

            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

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
                    "select \"user\".id, title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name, \"user\".company " +
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
                        resultSet.getLong(1),  // 1 equal user id
                        resultSet.getString("title"),
                        resultSet.getString("firstName"),
                        resultSet.getString("middleName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("createdAt"),
                        resultSet.getString("email"),
                        resultSet.getInt("role"));

        if (resultSet.getString("name") != null)
        {
            var company = new CompanyEntity(
                    resultSet.getLong(12), //12 equal company id
                    resultSet.getString("name"));
            user.setCompanyEntity(company);
            //user.setCompanyName(company.getName());
        }

        //Makes a recursion call. It will loop through until an user isn't createdBy is null
        if (resultSet.getString("createdBy") != null)
        {
            user.setCreatedBy(getUserById(resultSet.getLong("createdBy")));
            //user.setCreatedByName(user.getCreatedBy().getFirstName());
        }

        return user;

    }
}
