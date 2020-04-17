package org.example.domain;
import org.example.entity.*;
import java.util.List;

public interface IProgram  {
    public long createProgram(ProgramEntity program);
    public Boolean deleteProgram(ProgramEntity program);
    public Boolean updateProgram(ProgramEntity program);
    public List<ProgramEntity> getAllPrograms();
    public ProgramEntity getProgramById(ProgramEntity id);
    public List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity);
    public List<ProgramEntity> getProgramsByProducer(UserEntity user);

    boolean removeUserFromProgram(UserEntity user, long programId);
    boolean removeCreditFromProgram(CreditEntity creditEntity);
}
