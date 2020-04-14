package org.example.domain;

import org.example.entity.CompanyEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;


import java.util.List;

public class Program implements IProgram {
    private IPersistenceHandler persistenceHandler = new PersistenceHandler();



    public long createProgram(ProgramEntity programEntity){
        return persistenceHandler.program().createProgram(programEntity);
    }
    public Boolean deleteProgram(ProgramEntity programEntity){
        return persistenceHandler.program().deleteProgram(programEntity);
    }
    public Boolean updateProgram(ProgramEntity programEntity){
        return persistenceHandler.program().updateProgram(programEntity);
    }
    public List<ProgramEntity> getAllPrograms(){
        return persistenceHandler.program().getAllPrograms();
    }
    public ProgramEntity getProgramById(ProgramEntity id){
        return persistenceHandler.program().getProgramById(id);
    }
    public List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity){
        return persistenceHandler.program().getProgramsByCompany(companyEntity);
    }
    public List<ProgramEntity> getProgramsByProducer(UserEntity userEntity){
        return persistenceHandler.program().getProgramsByProducer(userEntity);
    }

}
