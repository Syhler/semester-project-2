package org.example.domain;

import org.example.entity.CompanyEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;

import java.util.List;

public class Program implements IProgram {
    private IPersistenceHandler persistenceHandler;


    public Boolean createProgram(ProgramEntity programEntity){
        return false;
    }
    public Boolean deleteProgram(ProgramEntity programentity){
        return false;
    }
    public Boolean updateProgram(ProgramEntity programEntity){
        return false;
    }
    public List<ProgramEntity> getAllPrograms(){
        return null;
    }
    public ProgramEntity getProgramById(String id){
        return null;
    }
    public List<Program> getProgramsByCompany(CompanyEntity company){
        return null;
    }
    public List<Program> getProgramsByProducer(UserEntity userEntity){
        return null;
    }

}
