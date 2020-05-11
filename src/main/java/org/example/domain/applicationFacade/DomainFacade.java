package org.example.domain.applicationFacade;

import org.example.domain.buisnessComponents.*;
import org.example.domain.mapper.CompanyMapper;
import org.example.domain.mapper.ProgramInformationMapper;
import org.example.domain.mapper.ProgramMapper;
import org.example.domain.mapper.UserMapper;
import org.example.domain.password.PasswordHashing;
import org.example.persistence.dataAccess.PersistenceHandler;
import org.example.persistence.common.IPersistenceHandler;
import org.example.persistence.entities.ProgramEntity;
import org.example.persistence.entities.UserEntity;

import java.util.List;

public class DomainFacade
{
    private IPersistenceHandler persistenceHandler = new PersistenceHandler();

    public Program getProgramById(long programId)
    {
        ProgramEntity programEntity = persistenceHandler.program().getProgramById(programId);

        return ProgramMapper.map(programEntity);
    }

    public List<Program> getAllPrograms()
    {
        List<ProgramEntity> programEntities = persistenceHandler.program().getAllPrograms();

        return ProgramMapper.map(programEntities);
    }

    public List<Program> getAllDeletedprogram(){
        List<ProgramEntity> programEntities = persistenceHandler.program().getAllDeletedProgram();
        return ProgramMapper.map(programEntities);
    }

    public List<User> getAllUsers()
    {
        List<UserEntity> userEntities = persistenceHandler.user().getAllUsers();

        return UserMapper.map(userEntities);
    }

    public List<User> getUserByRole(Role role)
    {
        var users = persistenceHandler.user().getUserByRole(role.getValue());

        return UserMapper.map(users);
    }

    public List<User> getDeletedUsers()
    {
        List<UserEntity> deletedUsers = persistenceHandler.user().getAllDeletedUsers();

        return UserMapper.map(deletedUsers);
    }

    public List<Company> getAllCompanies()
    {
        var companies = persistenceHandler.company().getAllCompanies();

        return CompanyMapper.map(companies);
    }

    public List<Company> getAllDeletedCompanies(){
        var deletedcompanies = persistenceHandler.company().getAllDeletedCompanies();
        return CompanyMapper.map(deletedcompanies);
    }


    public Program createProgram(ProgramInformation programInformation)
    {
        var programInformationEntity = ProgramInformationMapper.map(programInformation);

        var createdProgram = persistenceHandler.program().createProgram(programInformationEntity);

        return ProgramMapper.map(createdProgram);
    }

    public Program importPrograms(Program program)
    {
        var mapped = ProgramMapper.map(program);

        var importedPrograms = persistenceHandler.program().importPrograms(mapped);

        return ProgramMapper.map(importedPrograms);
    }

    public Authentication getAuthentication()
    {
        return new Authentication();
    }


    public Company createCompany(Company company)
    {
        var companyEntity = CompanyMapper.map(company);
        var id = persistenceHandler.company().createCompany(companyEntity);
        company.setId(id);
        return company;
    }

    public User createUser(User user, String password)
    {
        var userEntity = UserMapper.map(user);

        var passwordSalt = PasswordHashing.generateSalt();
        try {

            var encryptedPassword = PasswordHashing.sha256(password,passwordSalt);
            var id = persistenceHandler.user().createUser(userEntity, encryptedPassword, passwordSalt);
            user.setId(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    public List<Program> search(String query) {
        var programEntites = persistenceHandler.program().search(query);

        return ProgramMapper.map(programEntites);
    }
}


