package org.example.domain;

import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.persistence.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

import java.util.List;

public class Company implements ICompany {
    private IPersistenceHandler persistenceHandler = new PersistenceHandler();

    public Boolean createUser(UserEntity userEntity) {
        return false;
    }

    public Boolean removeUser(UserEntity userEntity) {
        return false;
    }

    public UserEntity getUserById(String id) {
        return null;
    }

    public List<UserEntity> getAllUser() {
        return null;
    }

    public Boolean updateUser(UserEntity userEntity) {
        return false;
    }

    public List<UserEntity> getUserByCompany(CompanyEntity company) {
        return null;

    }

    public List<UserEntity> getUserByRole(Role role) {

        return persistenceHandler.user().getUserByRole(role);

    }

    @Override
    public List<CompanyEntity> getCompanies() {
        return persistenceHandler.company().getCompanies();
    }

    @Override
    public boolean deleteCompany(CompanyEntity companyEntity) {
        return persistenceHandler.company().deleteCompany(companyEntity);
    }

    @Override
    public boolean updateCompany(CompanyEntity companyEntity) {
        return persistenceHandler.company().updateCompany(companyEntity);
    }

    @Override
    public Long createCompany(CompanyEntity companyEntity) {
        return persistenceHandler.company().createCompany(companyEntity);
    }

}
