package org.example.persistence;

import org.example.persistence.common.IPersistenceProgram;
import org.example.persistence.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PersistenceProgram extends BasePersistence implements IPersistenceProgram {

    private Connection connection;

    public PersistenceProgram() {
        connection = initializeDatabase();
    }

    /**
     * inserts given data into the programInformation table in the database
     * @param programEntity
     * @param language_id
     * @return
     */
    private long insertProgramInformation(ProgramEntity programEntity, long language_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO programInformation" +
                    "(description, title,language_id,program_id ) VALUES (?,?,?,?) RETURNING id");
            stmt.setString(1, programEntity.getProgramInformation().getDescription());
            stmt.setString(2, programEntity.getProgramInformation().getTitle());
            stmt.setLong(3, language_id);
            stmt.setLong(4, programEntity.getId());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // 1 is the index for id
                return resultSet.getLong("id");
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }


    /**
     * Inserts given data into the companyProgram table in the database
     * @param program_id
     * @param company_id
     * @return
     */
    private boolean insertCompany(long program_id, long company_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO companyProgram(program,company)" + "VALUES (?,?)");
            stmt.setLong(1, program_id);
            stmt.setLong(2, company_id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts a null timestamp into the program table in the database so that a program id is set.
     * @return the programs id.
     */
    private long insertProgram(){
       try {
           PreparedStatement statement = connection.prepareStatement(
                   "INSERT INTO program(timestamp_for_deletion) values (? ) RETURNING id");
           statement.setTimestamp(1,null);
           ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()) {
               return resultSet.getLong("id");
           }
           return 0;
       } catch (SQLException e){
           e.printStackTrace();
           return 0;
       }

    }

    /**
     * Inserts a null timestamp into the program table in the database so that a program id is set.
     * @return the programs id.
     */
    private long insertProgram(long programId){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO program(id,timestamp_for_deletion) values (?,? ) RETURNING id");
            statement.setLong(1, programId);
            statement.setTimestamp(2,null);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
            return 0;
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * Inserts all producers on the given program into the programproducer table in the database.
     * @param producer
     * @param programId
     */
    private void insertProducer(List<UserEntity> producer, long programId){
        try {
            PreparedStatement statementProducer = connection.prepareStatement(
                    "insert INTO programproducer(program_id, producer_id) " + "values (?,?)");
            connection.setAutoCommit(false);
            for (UserEntity user : producer) {
                statementProducer.setLong(1,programId);
                statementProducer.setLong(2,user.getId());
                statementProducer.addBatch();
            }
           statementProducer.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Inserts all credits on the given program into the credit table in the database.
     * @param credits
     * @param programId
     */
    private void insertCredit(List<CreditEntity> credits, long programId){
        try {
            PreparedStatement statementCredit  = connection.prepareStatement("INSERT INTO credit(program,\"user\") "
                    +"values(?,?)");
            connection.setAutoCommit(false);
        for ( CreditEntity credit: credits) {

            statementCredit.setLong(1,programId);
            statementCredit.setLong(2,credit.getActor().getId());
            statementCredit.addBatch();
            }
        statementCredit.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        }catch (SQLException e){
            e.printStackTrace();

        }

    }


    /**
     * creates a program.
     * @return
     */
    @Override
    public ProgramEntity createProgram(ProgramInformationEntity programInformation) {

        long programId = insertProgram();

        var program = new ProgramEntity(programId, programInformation);


        insertProgramInformation(program,1);

        return program;
    }

    @Override
    public List<ProgramEntity> importPrograms(List<ProgramEntity> programEntities) {

        for (var program : programEntities) {
            long programId = insertProgram();
            program.setId(programId);

            insertProgramInformation(program, 1);

            if (program.getCompanyEntity() != null)
            {
                if (program.getCompanyEntity().getId() == 0)
                {
                    new PersistenceHandler().company().createCompany(program.getCompanyEntity());
                }
                else
                {
                    insertCompany(program.getCompanyEntity().getId(), program.getId());
                }

            }
            if (program.getProducer() != null)
            {
                insertProducer(program.getProducer(),program.getId());
            }
            if (program.getCredits() != null)
            {
                insertCredit(program.getCredits(),program.getId());
            }
        }


        return programEntities;
    }

    /**
     * Sets a timestamp for when the program was deleted, doesnt delete anything in the database.
     * @param programID
     * @return
     */
    public boolean softDeleteProgramTable(long programID){
        try {
            PreparedStatement statement =connection.prepareStatement("UPDATE program SET timestamp_for_deletion = ? " +
                    "WHERE id = ? ");

            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setLong(2,programID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * uses other methods to determine how a program is deleted
     * @return
     */
    @Override
    public boolean deleteProgram(long programId) {
        return softDeleteProgramTable(programId);
    }

    /**
     * Checks if user exists if it doesnt it calls the createUser method
     * @param creditList
     */
    public void createUserIfDoesntExists(List<CreditEntity> creditList){


        for (CreditEntity credit: creditList) {
            if (credit.getActor().getId() == 0){
                long actorID = createUser(credit.getActor(),"a","b");
                credit.getActor().setId(actorID);
            }
        }

    }

    /**
     * creates a user in the database and maps the data to a userEntity.
     * @param userEntity
     * @param passwordSalt
     * @param encryptedPassword
     * @return UserEntity
     */

    public long createUser(UserEntity userEntity,String passwordSalt, String encryptedPassword) {
        java.util.Date utilDate = userEntity.getCreatedAt();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());



        try {

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "Insert INTO \"user\" (title,firstname, middlename, lastname, createdby, createdat, email, password,passwordsalt ,role, company)" +
                    " values (?,?,?,?,?,?,?,?,?,?,?) returning id;");

            preparedStatement.setString(1,userEntity.getTitle());
            preparedStatement.setString(2,userEntity.getFirstName());
            preparedStatement.setString(3,userEntity.getMiddleName());
            preparedStatement.setString(4,userEntity.getLastName());
            if (userEntity.getCreatedBy() != null){
                preparedStatement.setLong(5,userEntity.getCreatedBy().getId());
            }else preparedStatement.setNull(5, Types.BIGINT);

            preparedStatement.setDate(6,sqlDate);
            preparedStatement.setString(7,userEntity.getEmail());
            preparedStatement.setString(8,encryptedPassword);
            preparedStatement.setString(9,passwordSalt);
            if (userEntity.getRole() != 0){
                preparedStatement.setInt(10,userEntity.getRole());
            }
            if (userEntity.getCompanyEntity() != null){
                preparedStatement.setLong(11,userEntity.getCompanyEntity().getId());
            }else {
                preparedStatement.setNull(11,Types.BIGINT);
            }


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
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * checks if company has a reference to program in the database if it doesnt it creates one if it does it updates the
     * one aleready in the database
     * @param programEntity
     */
    public void checkCompanyProgram(ProgramEntity programEntity){
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("select company from companyprogram where program = ?");
            stmt.setLong(1,programEntity.getId());
            ArrayList<Long> companyList = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                companyList.add(resultSet.getLong("company"));
            }
            if (companyList.isEmpty()){
                initializeCompanyProgram(programEntity);
            }
            updateCompanyProgram(programEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }





    }

    /**
     * creates a reference between a company and a program.
     * @param programEntity
     */
    public void initializeCompanyProgram(ProgramEntity programEntity){

        try {

            PreparedStatement statement = connection.prepareStatement("insert into companyprogram(company, program) values (?,?)");
            statement.setLong(1,programEntity.getCompanyEntity().getId());
            statement.setLong(2,programEntity.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates companyprogram table based on the given data.
     * @param programEntity
     * @return
     */

    public boolean updateCompanyProgram(ProgramEntity programEntity){
        try {

            PreparedStatement statement1 = connection.prepareStatement("UPDATE companyprogram SET company =? where program = ?");
            statement1.setLong(1,programEntity.getCompanyEntity().getId());
            statement1.setLong(2,programEntity.getId());
            statement1.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * updates the credit table in the database.
     * @param programEntity
     * @return
     */
    public boolean updateCredit(ProgramEntity programEntity){

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT into credit(\"user\", program) values (?, ?)");
                connection.setAutoCommit(false);
            for (CreditEntity credit:programEntity.getCredits()) {
                if (credit.getProgramId() == 0){
                    statement.setLong(1,credit.getActor().getId());
                    statement.setLong(2,programEntity.getId());
                    statement.addBatch();
                }
            }
                statement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * updates the many to many table between producer and program.
     * @param programEntity
     * @return
     */
    public boolean updateProducerForProgram(ProgramEntity programEntity){
        try {
            PreparedStatement stmt = connection.prepareStatement("select producer_id  from programproducer where program_id = ?");
            stmt.setLong(1,programEntity.getId());
            ResultSet resultSet = stmt.executeQuery();
            PreparedStatement statement = connection.prepareStatement("insert into programproducer(producer_id, program_id) values (?,?)");
            connection.setAutoCommit(false);
            ArrayList<Long> idList = new ArrayList<>();
            while(resultSet.next()){
                    idList.add(resultSet.getLong("producer_id"));
            }
            ArrayList<Long> producerIdList = new ArrayList<>();

            if (!idList.isEmpty()){
                for (UserEntity producer: programEntity.getProducer()) {
                    producerIdList.add(producer.getId());
                    for (long id: idList) {
                        if (!producerIdList.contains(id)){
                            statement.setLong(1,producer.getId());
                            statement.setLong(2,programEntity.getId());
                            statement.addBatch();
                            producerIdList.add(id);
                        }
                    }
                }
            }else for (UserEntity producer: programEntity.getProducer()) {
                if (!producerIdList.contains(producer.getId())) {
                    statement.setLong(1, producer.getId());
                    statement.setLong(2, programEntity.getId());
                    statement.addBatch();
                    producerIdList.add(producer.getId());
                }
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * updates a programs infomation in the programinformation table.
     * @param programEntity
     * @return
     */
    @Override
    public boolean updateProgram(ProgramEntity programEntity) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE  programinformation SET title = ?, description = ? " +
                    " WHERE programinformation.program_id = ? ");
            stmt.setString(1,programEntity.getProgramInformation().getTitle());
            stmt.setString(2,programEntity.getProgramInformation().getDescription());
            stmt.setLong(3,programEntity.getId());

            createUserIfDoesntExists(programEntity.getCredits());
            updateProducerForProgram(programEntity);
            updateCredit(programEntity);
            checkCompanyProgram(programEntity);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Selects information on every actor on a given program.
     * @return list of all the actors on a program.
     */
    public ArrayList<CreditEntity> getCreditUser(long programId){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT credit.program, \"user\".id, \"user\".title, \"user\"" +
                    ".firstname, \"user\".middlename ,\"user\".lastname,\"user\".email from credit inner join " +
                    "\"user\" on credit.\"user\" = \"user\".id where credit.program = ? and timestamp_for_deletion is null ");
            statement.setLong(1,programId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CreditEntity> creditList = new ArrayList<>();
            while (resultSet.next()){
               CreditEntity credit = new CreditEntity(programId,createUserEntityFromResultSet(resultSet));
               creditList.add(credit);
            }
            return creditList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Selects information on all producers on a given program
     * @return list of all the users on a program.
     */
    private ArrayList<UserEntity> getProducerForProgram(long programId){
        try {
            PreparedStatement statement = connection.prepareStatement("select \"user\".id, \"user\".title,firstname, middlename, lastname, " +
                    "email from programproducer inner join \"user\" on programproducer.producer_id = \"user\".id " +
                    "where programproducer.program_id = ? and timestamp_for_deletion is null");
            statement.setLong(1,programId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<UserEntity> producerList = new ArrayList<>();
            while (resultSet.next()){
            UserEntity producer = createUserEntityFromResultSet(resultSet);
            producerList.add(producer);
            }
            return producerList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Selects information the company that is connected to the program
     * @return A company entity
     */
    private CompanyEntity getCompanyProgram(long programId){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT company.name , company.id from company" +
                    " inner join companyprogram  on company.id = companyprogram.company where program = ?");
            statement.setLong(1,programId);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new CompanyEntity(resultSet.getLong("id"),resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Returns information based on a given programs id.
     * @return A progrmaentity
     */
    @Override
    public ProgramEntity getProgramById(long programId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT programinformation.description ," +
                    " programinformation.title , programinformation.program_id FROM programinformation, program  where programinformation.program_id = ? ");
            stmt.setLong(1, programId);

            var resultSet = stmt.executeQuery();

            // checks if resultset contains any rows
            if (!resultSet.next()) {
                return null;
            }

            return new ProgramEntity(
                    resultSet.getLong("program_id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    getCompanyProgram(programId),
                    getProducerForProgram(programId),
                    getCreditUser(programId));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns title and id for all programs in the database.
     * @return all programs in the database.
     */
    @Override
    public List<ProgramEntity> getAllPrograms() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT distinct on (programinformation.program_id) programinformation.title ," +
                    "programinformation.program_id, timestamp_for_deletion from programinformation " +
                    "inner join program on programinformation.program_id = program.id ");



            ResultSet sqlReturnValues = stmt.executeQuery();
            List<ProgramEntity> returnvalue = new ArrayList<>();

            while (sqlReturnValues.next()) {
                if (sqlReturnValues.getTimestamp("timestamp_for_deletion")==null) {
                    returnvalue.add(new ProgramEntity(
                            sqlReturnValues.getLong("program_id"),
                            sqlReturnValues.getString("title")));
                }
            }
            return returnvalue;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns all programs a given producer has been involved with
     * @param producer
     * @return A list of programentitys
     */
    @Override
    public List<ProgramEntity> getProgramsByProducer(UserEntity producer) {

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT producer_id, programproducer.program_id, title from programproducer" +
                    " inner join programinformation on programproducer.program_id = programinformation.program_id " +
                    "where programproducer.producer_id = ?");
            stmt.setLong(1, producer.getId());
            ResultSet sqlReturn = stmt.executeQuery();
            ArrayList<ProgramEntity> programList = new ArrayList<>();
           while (sqlReturn.next()){
               ProgramEntity program = new ProgramEntity(sqlReturn.getLong("program_id"), sqlReturn.getString("title"));
               programList.add(program);

           }

            return programList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns all programs a company has been involved with
     * @param companyEntity
     * @return A list of all programs a company has been involved with.
     */
    @Override
    public List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("select title, company, program from companyprogram " +
                    "inner join programinformation on companyprogram.id = programinformation.program_id " +
                    "where companyprogram.company = ?");
            stmt.setLong(1, companyEntity.getId());
            ResultSet sqlReturn = stmt.executeQuery();

            ArrayList<ProgramEntity> programList = new ArrayList<>();
            while (sqlReturn.next()){
                ProgramEntity program = new ProgramEntity(sqlReturn.getLong("program"),sqlReturn.getString("title"));
                programList.add(program);
            }

            return programList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean removeUserFromProgram(UserEntity user, long programId)
    {
        try {
            PreparedStatement statement = connection.prepareStatement("update programProducer set timestamp_for_deletion = ? where program_id = ? and producer_id = ?");
            statement.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            statement.setLong(2, programId);
            statement.setLong(3, user.getId());

            var updatedRow = statement.executeUpdate();
            return updatedRow > 0;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCreditFromProgram(CreditEntity creditEntity) {
        try {
            PreparedStatement statement = connection.prepareStatement("update credit set timestamp_for_deletion = ? where program = ? and \"user\" = ?");
            statement.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            statement.setLong(2, creditEntity.getProgramId());
            statement.setLong(3, creditEntity.getActor().getId());

            var updatedRow = statement.executeUpdate();
            return updatedRow > 0;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }





    /**
     * Creates a userEntity based on a given resultSet.
     * @param resultSet
     * @return A user.
     * @throws SQLException
     */
    private UserEntity createUserEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserEntity(
                resultSet.getLong("id"),
                resultSet.getString("firstName"),
                resultSet.getString("middleName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getString("title"));

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
            var programs = new ArrayList<ProgramEntity>();

            Statement statement = connection.createStatement();

            var programResult = statement.executeQuery(sqlForProgram);
            programs.addAll(mapToProgramEntites(programResult, "program_id"));

            var creditResult = statement.executeQuery(sqlForCredit);
            var programsFromCredits = mapToProgramEntites(creditResult, "program");
            programs.addAll(programsFromCredits);

            var companyResult = statement.executeQuery(sqlForCompany);
            var programsFromCompany = mapToProgramEntites(companyResult, "id");
            programs.addAll(programsFromCompany);

            programs = (ArrayList<ProgramEntity>) programs.stream().filter(distinctByKey(ProgramEntity::getId)).collect(Collectors.toList());

            return programs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * who knows????????? https://stackoverflow.com/questions/23699371/java-8-distinct-by-property
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * builds a SQL query from the given words
     * @param words query split into words
     * @return SQL query to search on the company name
     */
    private String createCompanySearchSQL(String[] words)
    {
        /*
        StringBuilder baseSQL = new StringBuilder("select program, name, title" +
                " from companyprogram" +
                "    inner join programinformation p on companyProgram.program = p.id" +
                "    inner join company c on companyProgram.company = c.id");

         */

        StringBuilder baseSQL = new StringBuilder("select distinct on (program.id) program.id, name, title from companyprogram" +
                "    left join programinformation on companyProgram.program = programinformation.program_id" +
                "    left join company on companyProgram.company = company.id" +
                "    left join program on companyProgram.program = program.id");

        var company = "lower(company.name)";

        baseSQL.append(" ").append("where").append(" ").append(company);

        buildStringComponent(words, baseSQL, company);

        baseSQL.append(" ").append("and").append(" ").append("program.timestamp_for_deletion is null");

        baseSQL.append(" ").append("order by program.id");


        return baseSQL.toString();
    }

    /**
     * builds a SQL query from the given words
     * @param words query split into words
     * @return SQL query to search on credits full name
     */
    private String createCreditSearchSQL(String[] words)
    {
        /*
        StringBuilder baseSQL = new StringBuilder("select distinct(program), programinformation.title" +
                " from credit" +
                " inner join \"user\" on credit.\"user\" = \"user\".id" +
                " inner join programinformation on credit.program = programinformation.id");

         */

        StringBuilder baseSQL = new StringBuilder("select distinct(program), programinformation.title from credit inner join \"user\" on credit.\"user\" = \"user\".id " +
                "inner join programinformation on credit.program = programinformation.id " +
                "inner join program on programinformation.program_id = program.id");

        var fullName = "lower(concat(\"user\".firstName, ' ',\"user\".middleName,' ', \"user\".lastName))";

        baseSQL.append(" ").append("where").append(" ").append(fullName);

        buildStringComponent(words, baseSQL, fullName);

        baseSQL.append(" ").append("and").append(" ").append("program.timestamp_for_deletion is null");

        baseSQL.append(" ").append("order by program");

        return baseSQL.toString();
    }



    /**
     * builds a SQL query from the given words
     * @param words query split into words
     * @return SQL query to search on programs title
     */
    private String createProgramSearchSQL(String[] words) {
        StringBuilder baseSQL = new StringBuilder("SELECT distinct(program_id) ,title  from programinformation inner join program on programinformation.program_id = program.id");

        var title = "lower(title)";

        baseSQL.append(" ").append("where").append(" ").append(title);

        //title
        buildStringComponent(words, baseSQL, title);

        baseSQL.append(" ").append("and").append(" ").append("timestamp_for_deletion is null");

        baseSQL.append(" ").append("order by program_id");

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
                    resultSet.getLong(programIdName),
                    resultSet.getString("title"));

            tempList.add(program);
        }

        return tempList;
    }
}
