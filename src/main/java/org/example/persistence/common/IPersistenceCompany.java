package org.example.persistence.common;


import org.example.persistence.entities.CompanyEntity;
import org.example.persistence.entities.UserEntity;

import java.util.List;

public interface IPersistenceCompany
{

    List<CompanyEntity> getAllCompanies();
    boolean deleteCompany(CompanyEntity companyEntity);
    boolean updateCompany(CompanyEntity companyEntity);
    long createCompany(CompanyEntity companyEntity);
    List<CompanyEntity> getAllDeletedCompanies();
    Boolean unDeletecompany(CompanyEntity companyEntity);

}
