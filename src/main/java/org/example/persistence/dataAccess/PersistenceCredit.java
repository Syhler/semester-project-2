package org.example.persistence.dataAccess;


import org.example.persistence.common.IPersistenceCredit;
import org.example.persistence.entities.CreditEntity;

import java.sql.Connection;
import java.util.List;

public class PersistenceCredit extends BasePersistence implements IPersistenceCredit {

    private Connection connection;

    public PersistenceCredit()
    {
        connection = initializeDatabase();
    }


    @Override
    public boolean createCredit(CreditEntity creditEntity) {
        return false;
    }

    @Override
    public boolean deleteCredit(CreditEntity creditEntity) {
        return false;
    }

    @Override
    public boolean updateCredit(CreditEntity creditEntity) {
        return false;
    }

    @Override
    public List<CreditEntity> getAllCredits() {
        return null;
    }

    @Override
    public CreditEntity getCreditById(String id) {
        return null;
    }

    @Override
    public List<CreditEntity> getCreditsByProgramId(String id) {
        return null;
    }
}
