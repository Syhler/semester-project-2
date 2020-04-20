package org.example.persistence;


import org.example.persistence.entities.CompanyEntity;

import java.util.List;

public interface IPersistenceCompany
{

    List<CompanyEntity> getAllCompanies();
    boolean deleteCompany(CompanyEntity companyEntity);
    boolean updateCompany(CompanyEntity companyEntity);
    long createCompany(CompanyEntity companyEntity);

}
