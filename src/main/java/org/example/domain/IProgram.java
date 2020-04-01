package org.example.domain;
import org.example.entity.*;
import java.util.List;

public interface IProgram  {
    public Boolean createProgram(ProgramEntity program);
    public Boolean deleteProgram(ProgramEntity program);
    public Boolean updateProgram(ProgramEntity program);
    public List<ProgramEntity> getAllPrograms();
    public ProgramEntity getProgramById(String id);
    public List<Program> getProgramsByCompany(CompanyEntity companyEntity);
    public List<Program> getProgramsByProducer(UserEntity user);
}
