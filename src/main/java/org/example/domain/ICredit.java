package org.example.domain;
import org.example.entity.*;
import java.io.File;
import java.util.List;

public interface ICredit extends IDomainHandler {
   public List<CreditEntity> getAllCredits();
   public List<CreditEntity> ImportCredit(File file);
   public File ExportCredit(String id);
   public List<Credit> getCreditsByProgram(String id);






}
