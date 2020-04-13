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

    /**
     * Search in the database for program title, credit full name and company on the given query
     * @param query the query to serach from
     * @return a list of ProgramEntites
     */
    @Override
    public List<ProgramEntity> search(String query) {
        var words = query.toLowerCase().split(" ");
        var sqlForProgram = createProgramSearchSQL(words);
        var sqlForCredit = createCreditSearchSQL(words);
        var sqlForCompany = createCompanySearchSQL(words);

        try {
            var program = new ArrayList<ProgramEntity>();

            Statement statement = connection.createStatement();

            var programResult = statement.executeQuery(sqlForProgram);
            program.addAll(mapToProgramEntites(programResult, "program_id"));

            var creditResult = statement.executeQuery(sqlForCredit);
            program.addAll(mapToProgramEntites(creditResult, "program"));

            var companyResult = statement.executeQuery(sqlForCompany);
            program.addAll(mapToProgramEntites(companyResult, "program"));

            return program;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * builds a SQL query from the given words
     * @param words query split into words
     * @return SQL query to search on the company name
     */
    private String createCompanySearchSQL(String[] words)
    {
        StringBuilder baseSQL = new StringBuilder("select program, name, title" +
                " from companyprogram" +
                "    inner join programinformation p on companyProgram.program = p.id" +
                "    inner join company c on companyProgram.company = c.id");

        var company = "lower(name)";

        baseSQL.append(" ").append("where").append(" ").append(company);

        buildStringComponent(words, baseSQL, company);


        return baseSQL.toString();
    }

    /**
     * builds a SQL query from the given words
     * @param words query split into words
     * @return SQL query to search on credits full name
     */
    private String createCreditSearchSQL(String[] words)
    {
        StringBuilder baseSQL = new StringBuilder("select distinct(program), programinformation.title" +
                " from credit" +
                " inner join \"user\" on credit.\"user\" = \"user\".id" +
                " inner join programinformation on credit.program = programinformation.id");

        var fullName = "lower(concat(\"user\".firstName, ' ',\"user\".middleName,' ', \"user\".lastName))";

        baseSQL.append(" where ").append(fullName);

        buildStringComponent(words, baseSQL, fullName);

        baseSQL.append(" ").append("order by program");

        return baseSQL.toString();
    }



    /**
     * builds a SQL query from the given words
     * @param words query split into words
     * @return SQL query to search on programs title
     */
    private String createProgramSearchSQL(String[] words) {
        StringBuilder baseSQL = new StringBuilder("SELECT title, program_id from programinformation");
        var title = "lower(title)";

        baseSQL.append(" where ").append(title);

        //title
        buildStringComponent(words, baseSQL, title);

        return baseSQL.toString();
    }

    /**
     * builds the search part of an SQL query.
     * @param words list of words used in the query
     * @param baseSql already written SQL
     */
    private void buildStringComponent(String[] words, StringBuilder baseSql, String columnSQL) {
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            baseSql.append(" like '%").append(word).append("%'");

            if (i < words.length - 1) {
                baseSql.append(" and ").append(columnSQL);
            }

        }
    }

    /**
     * Loops through the result set and maps its data to a ProgramEntity object
     * @param resultSet result set
     * @param programIdName the column name of the resultSet programID
     * @return a list of programEntites
     */
    private List<ProgramEntity> mapToProgramEntites(ResultSet resultSet, String programIdName) throws SQLException {

        var tempList = new ArrayList<ProgramEntity>();

        while (resultSet.next()) {

            var program = new ProgramEntity(
                    resultSet.getString("title"),
                    null,
                    null,
                    null,
                    null);
            program.setId(resultSet.getLong(programIdName));

            tempList.add(program);
        }

        return tempList;
    }
}
