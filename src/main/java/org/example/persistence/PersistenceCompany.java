package org.example.persistence;

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
                    "SELECT * FROM \"company\"");

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
    private CompanyEntity createCompanyEntityFromResultSet(ResultSet resultSet) throws SQLException {
        var company = new CompanyEntity(resultSet.getString("name"));
        company.setId(resultSet.getLong("id"));

        return company;

    }
}
