package org.example.domain;
import org.example.entity.*;
import java.io.File;
import java.util.List;

public interface ICredit  {
   public List<CreditEntity> getAllCredits();
   public List<CreditEntity> importCredit(File file);
   public File exportCredit(String id);
   public List<Credit> getCreditsByProgram(String id);






}
