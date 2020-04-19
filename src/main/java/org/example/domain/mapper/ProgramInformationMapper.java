package org.example.domain.mapper;


import org.example.domain.Language;
import org.example.domain.ProgramInformation;
import org.example.persistence.entities.ProgramInformationEntity;

public class ProgramInformationMapper
{

    public static ProgramInformation map(ProgramInformationEntity programInformationEntity)
    {
        if (programInformationEntity == null)
        {
            return null;
        }

        return new ProgramInformation(
                programInformationEntity.getId(),
                programInformationEntity.getTitle(),
                programInformationEntity.getDescription(),
                Language.getLanguageById(programInformationEntity.getLanguage()));
    }

    public static ProgramInformationEntity map(ProgramInformation programInformation)
    {
        if (programInformation == null)
        {
            return null;
        }

        return new ProgramInformationEntity(
                programInformation.getId(),
                programInformation.getTitle(),
                programInformation.getDescription(),
                programInformation.getLanguage().getValue()
        );
    }


}
