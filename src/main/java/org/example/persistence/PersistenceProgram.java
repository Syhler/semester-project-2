package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;

import java.sql.Connection;
import java.util.List;

public class PersistenceProgram extends BasePersistence implements  IPersistenceProgram {

    private Connection connection;

    public PersistenceProgram()
    {
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
}
