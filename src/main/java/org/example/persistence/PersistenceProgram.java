package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceProgram extends BasePersistence implements IPersistenceProgram {

    private Connection connection;

    public PersistenceProgram() {
        connection = initializeDatabase();
    }


    @Override
    public boolean createProgram(ProgramEntity programEntity) {
        return false;
    }

    @Override
    public boolean deleteProgram(ProgramEntity programEntity) {
        return false;
    }

    @Override
    public boolean updateProgram(ProgramEntity programEntity) {
        return false;
    }

    @Override
    public ProgramEntity getProgramById(String id) {
        return null;
    }

    @Override
    public List<ProgramEntity> getAllPrograms() {
        return null;
    }

    @Override
    public List<ProgramEntity> getProgramsByProducer(UserEntity producer) {
        return null;
    }

    @Override
    public List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity) {
        return null;
    }

    @Override
    public List<ProgramEntity> search(String query) {
        var words = query.split(" ");
        var sqlForProgram = createSearchSQL(words);

        try {
            Statement statement = connection.createStatement();
            var programResult = statement.executeQuery(sqlForProgram);
            //var creditResult = statement.executeQuery("");

            return mapToProgramEntites(programResult);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<ProgramEntity> mapToProgramEntites(ResultSet resultSet) throws SQLException {

        var tempList = new ArrayList<ProgramEntity>();

        while (resultSet.next()) {

            var program = new ProgramEntity(
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    null,
                    null,
                    null);
            program.setId(resultSet.getLong("program_id"));

            tempList.add(program);
        }

        return tempList;

    }

    private String createSearchSQL(String[] words) {
        StringBuilder baseSql = new StringBuilder("SELECT description, title, program_id from programinformation");
        var title = "lower(title)";
        var description = "lower(description)";

        baseSql.append(" where ").append(title);

        //title
        buildStringComponent(words, baseSql, title);

        baseSql.append(" or ").append(description);

        //description
        buildStringComponent(words, baseSql, description);


        return baseSql.toString();
    }

    private void buildStringComponent(String[] words, StringBuilder baseSql, String coulmnName) {
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            baseSql.append(" like '%").append(word).append("%'");

            if (i < words.length - 1) {
                baseSql.append(" and ").append(coulmnName);
            }

        }
    }
}
