package org.example.domain;

import java.util.List;

public class Program implements IProgram {
    private IPersistenceHandler persistenceHandler;


    public Boolean createProgram(ProgramEntity programEntity){
        return false;
    }
    public Boolean deleteProgram(ProgramEntity programEntity){
        return false;
    }
    public Boolean updateporgam(ProgramEntity programEntity){
        return false;
    }
    public List<ProgramEntity> getAllPrograms(){
        return null;
    }
    public ProgramEntity getProgramByld(String id){
        return null;
    }
    public List<Program> getProgramsByCompany(CompanyEntity companyEntity){
        return null;
    }
    public List<Program> getProgramsByProducer(UserEntity userEntity){
        return null;
    }
}
