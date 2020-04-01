package org.example.persistence;

import org.example.entity.CreditEntity;

import java.util.List;

public interface IPersistenceCredit
{
    boolean createCredit(CreditEntity creditEntity);
    boolean deleteCredit(CreditEntity creditEntity);
    boolean updateCredit(CreditEntity creditEntity);
    List<CreditEntity> getAllCredits();
    CreditEntity getCreditById(String id);
    List<CreditEntity> getCreditsByProgramId(String id);
}
