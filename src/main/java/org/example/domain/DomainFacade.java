package org.example.domain;





import org.example.domain.mapper.ProgramInformationMapper;
import org.example.domain.mapper.ProgramMapper;
import org.example.domain.mapper.UserMapper;
import org.example.persistence.PersistenceHandler;
import org.example.persistence.entities.ProgramEntity;
import org.example.persistence.entities.UserEntity;

import java.util.List;

public class DomainFacade
{
    private PersistenceHandler persistenceHandler = new PersistenceHandler();

    public Program getProgramById(int programId)
    {
        ProgramEntity programEntity = persistenceHandler.program().getProgramById(programId);

        return ProgramMapper.map(programEntity);
    }

    public List<Program> getAllPrograms()
    {
        List<ProgramEntity> programEntities = persistenceHandler.program().getAllPrograms();

        return ProgramMapper.map(programEntities);
    }

    public List<User> getAllUsers()
    {
        List<UserEntity> userEntities = persistenceHandler.user().getAllUsers();

        return UserMapper.map(userEntities);
    }

    public Program createProgram(ProgramInformation programInformation)
    {
        var programInformationEntity = ProgramInformationMapper.map(programInformation);

        var createdProgram = persistenceHandler.program().createProgram(programInformationEntity);

        return ProgramMapper.map(createdProgram);
    }

    public Program createProgram(List<ProgramInformation> programInformations)
    {
        return null;
    }

    public Company createCompany()
    {
        return null;
    }

    public User createUser()
    {
        return null;
    }


}


