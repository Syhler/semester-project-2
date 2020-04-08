package org.example.persistence;

import org.example.domain.password.PasswordHashing;
import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;

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
    public Long createUser(UserEntity userEntity ,String password) {
        java.util.Date utilDate = userEntity.getCreatedAt();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        var passwordSalt = PasswordHashing.generateSalt();

        List<UserEntity> users = getAllUsers();
        Long id = users.get(users.size()-1).getId();
        id++;

        try {
            var encryptedPassword = PasswordHashing.sha256(password,passwordSalt);

            /*
            System.out.println(userEntity.getTitle());
            System.out.println(userEntity.getFirstName());
            System.out.println(userEntity.getMiddleName());
            System.out.println(userEntity.getLastName());
            System.out.println(createdBy.getId());
            System.out.println(sqlDate);
            System.out.println(userEntity.getEmail());
            System.out.println(encryptedPassword);
            System.out.println(passwordSalt);
            System.out.println(userEntity.getRole().getValue());
            System.out.println(userEntity.getCompany().getId());

             */

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "Insert INTO \"user\" (id, title,firstname, middlename, lastname, createdby, createdat, email, password, passwordsalt, role, company)" +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?) returning id;");
            preparedStatement.setLong(1,id);
            preparedStatement.setString(2,userEntity.getTitle());
            preparedStatement.setString(3,userEntity.getFirstName());
            preparedStatement.setString(4,userEntity.getMiddleName());
            preparedStatement.setString(5,userEntity.getLastName());
            preparedStatement.setLong(6,userEntity.getCreatedBy().getId());
            preparedStatement.setDate(7,sqlDate);
            preparedStatement.setString(8,userEntity.getEmail());
            preparedStatement.setString(9,encryptedPassword);
            preparedStatement.setString(10,passwordSalt);
            preparedStatement.setInt(11,userEntity.getRole().getValue());
            preparedStatement.setLong(12,userEntity.getCompany().getId());

            var resultSet = preparedStatement.executeQuery();
            //checks if the resultSet contains any rows
            if (!resultSet.next())
            {
                return 0L;
            }


                return resultSet.getLong("id");

        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }

    }

    /**
     * Search the database for a user based on the given ID
     * @param id of the user you want to find
     * @return a UserEntity object if a user has been found
     */
    @Override
    public UserEntity getUserById(Long id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id, title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name " +
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
    public boolean updateUser(UserEntity userEntity, String password) {
        java.util.Date utilDate = userEntity.getCreatedAt();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        var passwordSalt = PasswordHashing.generateSalt();

        try {
            var encryptedPassword = PasswordHashing.sha256(password,passwordSalt);

            /*
            System.out.println(userEntity.getTitle());
            System.out.println(userEntity.getFirstName());
            System.out.println(userEntity.getMiddleName());
            System.out.println(userEntity.getLastName());
            System.out.println(createdBy.getId());
            System.out.println(sqlDate);
            System.out.println(userEntity.getEmail());
            System.out.println(encryptedPassword);
            System.out.println(passwordSalt);
            System.out.println(userEntity.getRole().getValue());
            System.out.println(userEntity.getCompany().getId());

             */

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE \"user\" SET title = ?, firstname = ?,middlename = ?," +
                    "lastname = ?,createdby = ?,createdat = ?,email = ?,password = ?,passwordsalt = ?,role = ?,company = ?" +
                    "WHERE \"user\".id = ?;");
            preparedStatement.setString(1,userEntity.getTitle());
            preparedStatement.setString(2,userEntity.getFirstName());
            preparedStatement.setString(3,userEntity.getMiddleName());
            preparedStatement.setString(4,userEntity.getLastName());
            preparedStatement.setLong(5,userEntity.getCreatedBy().getId());
            preparedStatement.setDate(6,sqlDate);
            preparedStatement.setString(7,userEntity.getEmail());
            preparedStatement.setString(8,encryptedPassword);
            preparedStatement.setString(9,passwordSalt);
            preparedStatement.setInt(10,userEntity.getRole().getValue());
            preparedStatement.setLong(11,userEntity.getCompany().getId());
            preparedStatement.setLong(12,userEntity.getId());

            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    public List<UserEntity> getUserByRole(Role role) {
        List<UserEntity> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id, title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name " +
                    "from \"user\", company where \"user\".role = ?  and company.id = \"user\".company");
            preparedStatement.setInt(1, role.getValue());

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
                    "select \"user\".id, title, firstName, middleName, lastName, createdBy, createdAt, email, role,  company.id, company.name " +
                    "from \"user\", company");

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
        company.setId(resultSet.getLong(9)); //9 equal company id
        user.setCompany(company);
        user.setCompanyName(company.getName());
        user.setRole(resultSet.getInt("role"));
        user.setId(resultSet.getLong(1)); // 1 equal user id
        //user.setTitle(resultSet.getString("title"));

        //Makes a recursion call. It will loop through until an user isn't createdBy is null
        if (resultSet.getString("createdBy") != null)
        {
            user.setCreatedBy(getUserById(resultSet.getLong("createdBy")));
            user.setCreatedByName(user.getCreatedBy().getFirstName());
        }

        return user;

    }
}
