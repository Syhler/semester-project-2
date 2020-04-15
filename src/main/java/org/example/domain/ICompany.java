package org.example.domain;

import org.example.entity.CompanyEntity;

import java.util.List;

public interface ICompany {
   public List<CompanyEntity> getCompanies();
   public boolean deleteCompany(CompanyEntity companyEntity);
   public boolean updateCompany(CompanyEntity companyEntity);
   public Long createCompany(CompanyEntity companyEntity);

   }
