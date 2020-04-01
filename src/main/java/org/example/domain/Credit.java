package org.example.domain;

import java.io.File;
import java.util.List;

public class Credit implements ICredit {
    private PersistenceHandler persistenceHandler;

    public List<CreditEntity> getallCredits(){}
    public List<CreditEntity> importCredit(File file){}
    public File exportCredit(String credit){}
    public List<Credits> getCreditsByProgram(String id){}


}
