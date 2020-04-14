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

public class PersistenceCompany extends BasePersistence implements IPersistenceCompany {


    private Connection connection;

    public PersistenceCompany()
    {
        connection = initializeDatabase();
    }


    @Override
    public List<CompanyEntity> getCompanies() {
        List<CompanyEntity> companies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM \"company\" ORDER BY id ASC");

            var resultSet = preparedStatement.executeQuery();

            //checks if the resultSet contains any rows
            while (resultSet.next()){
                companies.add(createCompanyEntityFromResultSet(resultSet));
            }

            return companies;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteCompany(CompanyEntity companyEntity) {

            try {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "DELETE FROM company WHERE \"company\".id = ? ");
                preparedStatement.setLong(1,companyEntity.getId());
                preparedStatement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
    }

    @Override
    public boolean updateCompany(CompanyEntity companyEntity) {
        Long id = companyEntity.getId();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE company SET name = ? WHERE \"company\".id = ?");

            preparedStatement.setString(1,companyEntity.getName());
            preparedStatement.setLong(2,id);


            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Long createCompany(CompanyEntity companyEntity) {
        List<CompanyEntity> companies = getCompanies();
        Long id = companies.get(companies.size()-1).getId();
        id++;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "Insert INTO \"company\" (id, name)" +
                    " values (?,?) returning id;");
            preparedStatement.setLong(1,id);
            preparedStatement.setString(2,companyEntity.getName());

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

    private CompanyEntity createCompanyEntityFromResultSet(ResultSet resultSet) throws SQLException {
        var company = new CompanyEntity(resultSet.getString("name"));
        company.setId(resultSet.getLong("id"));

        return company;

    }
}
