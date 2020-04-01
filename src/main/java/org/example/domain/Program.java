package org.example.domain;

import java.util.List;

public class Program implements IProgram {
    private IPersistenceHandler persistenceHandler;

    public Boolean createProgram(ProgramEntity programEntity){}
    public Boolean deleteProgram(ProgramEntity programentity){}
    public Boolean updateporgam(ProgramEntity programEntity){}
    public List<ProgramEntity> getAllPrograms(){}
    public ProgramEntity getProgramByld(String id){}
    public List<Program> getProgramsByCompany(Company company){}
    public List<Program> getProgramsByProducer(UserEntity userEntity){}
}
