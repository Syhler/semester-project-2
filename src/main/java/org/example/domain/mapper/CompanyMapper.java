package org.example.domain.mapper;


import org.example.domain.Company;
import org.example.persistence.entities.CompanyEntity;

import java.util.ArrayList;
import java.util.List;

public class CompanyMapper
{

    public static List<Company> map (List<CompanyEntity> companyEntities)
    {
        if (companyEntities == null)
        {
            return null;
        }

        var temp = new ArrayList<Company>();

        for (var companyEntity: companyEntities) {
            temp.add(map(companyEntity));
        }

        return temp;
    }

    public static Company map (CompanyEntity companyEntity)
    {
        if (companyEntity == null)
        {
            return null;
        }

        return new Company(
                companyEntity.getId(),
                companyEntity.getName());
    }

    public static CompanyEntity map(Company company)
    {
        if (company == null) return null;

        return new CompanyEntity(
                company.getId(),
                company.getName()
        );
    }
}
