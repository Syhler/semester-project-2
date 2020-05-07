package org.example.persistence.common;


import org.example.persistence.entities.CompanyEntity;

import java.util.List;

public interface IPersistenceCompany
{

    List<CompanyEntity> getAllCompanies();
    List<CompanyEntity> getAllDeletedCompanies();
    boolean deleteCompany(CompanyEntity companyEntity);
    boolean unDeleteCompany(CompanyEntity companyEntity);
    boolean updateCompany(CompanyEntity companyEntity);
    long createCompany(CompanyEntity companyEntity);

}
