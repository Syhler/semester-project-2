package org.example.domain.mapper;


import org.example.domain.Credit;
import org.example.persistence.entities.CreditEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreditMapper
{

    public static List<Credit> map(List<CreditEntity> creditEntities)
    {
        if (creditEntities == null) return null;

        var temp = new ArrayList<Credit>();

        for (var creditEntity: creditEntities)
        {
            temp.add(map(creditEntity));
        }

        return temp;

    }


    public static List<CreditEntity> mapCreditToEntity(List<Credit> credits)
    {
        if (credits == null) return null;

        var temp = new ArrayList<CreditEntity>();

        for (var credit : credits) {
            temp.add(map(credit));
        }
        return temp;
    }

    public static Credit map(CreditEntity creditEntity)
    {
        if (creditEntity == null)
        {
            return null;
        }

        return new Credit(
                creditEntity.getProgramId(),
                UserMapper.map(creditEntity.getActor()));

    }

    public static CreditEntity map(Credit credit)
    {
        if (credit == null) return null;

        return new CreditEntity(
                credit.getProgramId(),
                UserMapper.map(credit.getUser())
        );

    }

}
