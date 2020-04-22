package org.example.persistence.common;


import org.example.domain.ProgramInformation;
import org.example.persistence.entities.*;

import java.util.List;

public interface IPersistenceProgram
{
    ProgramEntity createProgram(ProgramInformationEntity programEntity);
    List<ProgramEntity> importPrograms(List<ProgramEntity> programEntities);
    boolean deleteProgram(long programId);
    boolean updateProgram(ProgramEntity programEntity);
    ProgramEntity getProgramById(long id);
    List<ProgramEntity> getAllPrograms();
    List<ProgramEntity> getProgramsByProducer(UserEntity producer);
    List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity);
    boolean removeUserFromProgram(UserEntity user, long programId);

    boolean removeCreditFromProgram(CreditEntity creditEntity);

    List<ProgramEntity> search(String query);
}
