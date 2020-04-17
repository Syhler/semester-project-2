package org.example.persistence;

import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;

import java.util.List;

public interface IPersistenceProgram
{
    long createProgram(ProgramEntity programEntity);
    boolean deleteProgram(ProgramEntity programEntity);
    boolean updateProgram(ProgramEntity programEntity);
    ProgramEntity getProgramById(ProgramEntity programEntity);
    List<ProgramEntity> getAllPrograms();
    List<ProgramEntity> getProgramsByProducer(UserEntity producer);
    List<ProgramEntity> getProgramsByCompany(CompanyEntity companyEntity);
    boolean removeUserFromProgram(UserEntity user, long programId);

    boolean removeCreditFromProgram(CreditEntity creditEntity);

    List<ProgramEntity> search(String query);
}
