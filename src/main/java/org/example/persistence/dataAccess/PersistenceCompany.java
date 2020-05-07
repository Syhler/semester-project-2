package org.example.persistence.dataAccess;

import org.example.persistence.common.IPersistenceCompany;
import org.example.persistence.entities.CompanyEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceCompany extends BasePersistence implements IPersistenceCompany {


    private Connection connection;

    public PersistenceCompany()
    {
        connection = initializeDatabase();
    }


    @Override
    public List<CompanyEntity> getAllCompanies() {
        List<CompanyEntity> companies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM \"company\" ORDER BY id ASC");

            var resultSet = preparedStatement.executeQuery();

            //checks if the resultSet contains any rows
            while (resultSet.next()){
                if (resultSet.getTimestamp("timestamp_for_deletion") == null) {
                    companies.add(createCompanyEntityFromResultSet(resultSet));
                }
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
                        "UPDATE company set timestamp_for_deletion = ? WHERE \"company\".id = ? ");
                preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setLong(2,companyEntity.getId());
                var rows = preparedStatement.executeUpdate();
                return rows > 0;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    @Override
    public boolean updateCompany(CompanyEntity companyEntity) {
        long id = companyEntity.getId();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE company SET name = ? WHERE \"company\".id = ?");

            preparedStatement.setString(1,companyEntity.getName());
            preparedStatement.setLong(2,id);

            var rows = preparedStatement.executeUpdate();
            return rows > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<CompanyEntity> getAllDeletedCompanies(){
        List<CompanyEntity> companies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM \"company\" where timestamp_for_deletion is null ");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                companies.add(createCompanyEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean unDeletecompany(CompanyEntity companyEntity){

        try {
            PreparedStatement preparedStatement  = connection.prepareStatement("UPDATE company set timestamp_for_deletion = ? where id = ?");
            preparedStatement.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(2,companyEntity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public long createCompany(CompanyEntity companyEntity) {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "Insert INTO \"company\" (name, company.timestamp_for_deletion)" +
                    " values (?,?) returning id;");
            preparedStatement.setString(1,companyEntity.getName());
            preparedStatement.setTimestamp(2,null);
            var resultSet = preparedStatement.executeQuery();
            //checks if the resultSet contains any rows
            if (!resultSet.next())
            {
                return 0;
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

        return new CompanyEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"));

    }
}
