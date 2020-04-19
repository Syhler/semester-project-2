package org.example.domain.mapper;


import org.example.domain.Program;
import org.example.persistence.entities.ProgramEntity;

import java.util.ArrayList;
import java.util.List;

public class ProgramMapper
{

    public static List<Program> map(List<ProgramEntity> programEntities)
    {
        if (programEntities == null)
        {
            return null;
        }

        var temp = new ArrayList<Program>();

        for (var programEntity : programEntities)
        {
            temp.add(map(programEntity));
        }

        return temp;

    }

    public static ProgramEntity map(Program program)
    {
        if (program == null) return null;

        return new ProgramEntity(
                program.getId(),
                ProgramInformationMapper.map(program.getProgramInformation()),
                UserMapper.mapUserToEntity(program.getProducers()),
                CreditMapper.mapCreditToEntity(program.getCredits()),
                CompanyMapper.map(program.getCompany())
        );
    }

    public static Program map(ProgramEntity programEntity)
    {
        if (programEntity == null) return null;



        return new Program(
                programEntity.getId(),
                ProgramInformationMapper.map(programEntity.getProgramInformation()),
                CreditMapper.map(programEntity.getCredits()),
                UserMapper.map(programEntity.getProducer()),
                CompanyMapper.map(programEntity.getCompanyEntity())
        );
    }
}
