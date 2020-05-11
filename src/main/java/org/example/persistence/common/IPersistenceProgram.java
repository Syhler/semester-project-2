package org.example.persistence.common;


import org.example.persistence.entities.*;

import java.util.List;

public interface IPersistenceProgram
{
    ProgramEntity createProgram(ProgramInformationEntity programEntity);
    ProgramEntity importPrograms(ProgramEntity programEntity);
    boolean deleteProgram(long programId);
    boolean updateProgram(ProgramEntity programEntity);
    ProgramEntity getProgramById(long id);
    List<ProgramEntity> getAllPrograms();
    List<ProgramEntity> getProgramsByProducer(UserEntity producer);
    List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity);
    List<ProgramEntity> getAllDeletedProgram();
    boolean unDeleteProgram(long programid);
    boolean removeUserFromProgram(UserEntity user, long programId);
    boolean removeCreditFromProgram(CreditEntity creditEntity);

    List<ProgramEntity> search(String query);
}
