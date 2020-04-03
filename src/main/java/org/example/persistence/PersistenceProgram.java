package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistenceProgram extends BasePersistence implements IPersistenceProgram {

    private Connection connection;

    public PersistenceProgram() {
        connection = initializeDatabase();
    }

    private int programInformation(String description, String titel, int language_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO programInformation(description, titel,language_id )" + "VALUES (?,?,?)");
            stmt.setString(1, description);
            stmt.setString(2, titel);
            stmt.setInt(3, language_id);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.getInt("programinformation_id");

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
    private int credit(String user, String program) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO credits(\"user\", program)"+"VALUES (?,?)");
            stmt.setString(1, user);
            stmt.setString(2,program);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.getInt("credit_id");

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private int companyProgram(String program_id, int company) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO companyProgram(program_id,company)"+"VALUES (?,?)");
            stmt.setString(1, program_id);
            stmt.setInt(2,company);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.getInt("company_id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int language(String name){
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO language(name)" + "VALUES (?) ");
            stmt.setString(1,name);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.getInt("language_id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public boolean createProgram(ProgramEntity programEntity) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO program(name)"
                    + "VALUES (?) ");
            statement.setString(1, programEntity.getName());
            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProgram(ProgramEntity programEntity) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM program WHERE id = ?");
            stmt.setString(1, programEntity.getId());
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateProgram(ProgramEntity programEntity) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE table program WHERE ?")
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public ProgramEntity getProgramById(String id) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM program WHERE id = ?");
            stmt.setString(1, id);
            ResultSet sqlReturn = stmt.executeQuery();
            if (!sqlReturn.next()) {
                return null;
            }
            return new ProgramEntity(sqlReturn.getString(1));
            sqlReturn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProgramEntity> getAllPrograms() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM program WHERE program = ?");
            stmt.setString(1, (getAllPrograms()));
            ResultSet sqlReturn = stmt.executeQuery();
            if (!sqlReturn.next()) {
                return null;
            }
            return new ProgramEntity(sqlReturn.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProgramEntity> getProgramsByProducer(UserEntity producer) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM program WHERE Producer = ?");
            stmt.setString(1, producer.getId());
            ResultSet sqlReturn = stmt.executeQuery();
            if (!sqlReturn.next()) {
                return null;
            }
            return new ProgramEntity(sqlReturn.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM program WHERE id = ?");
            stmt.setString(1, companyEntity.getId());
            ResultSet sqlReturn = stmt.executeQuery();
            if (!sqlReturn.next()) {
                return null;
            }
            return new ProgramEntity(sqlReturn.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
