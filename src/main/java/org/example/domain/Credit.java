package org.example.domain;

import org.example.entity.CreditEntity;
import org.example.persistence.PersistenceHandler;

import java.io.File;
import java.util.List;

public class Credit implements ICredit
{
    private PersistenceHandler persistenceHandler;

    public List<CreditEntity> getAllCredits(){
        return null;
    }
    public List<CreditEntity> importCredit(File file){
        return null;
    }
    public File exportCredit(String credit){
        return null;
    }
    public List<Credit> getCreditsByProgram(String id){
        return null;
    }


}
