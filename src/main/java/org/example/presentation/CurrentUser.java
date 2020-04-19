package org.example.presentation;

import org.example.OLDentity.ProgramEntity;
import org.example.OLDentity.Role;
import org.example.OLDentity.UserEntity;

public class CurrentUser {

    private static CurrentUser single_instance = null;
    private UserEntity currentUser;

    public static CurrentUser getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentUser();

        return single_instance;
    }

    public UserEntity getUserEntity() {
        return currentUser;
    }

    public void init(UserEntity userEntity) {
        this.currentUser = userEntity;
    }

    public boolean gotAccessToProgram(ProgramEntity programEntity)
    {
        if (currentUser.getRole() == Role.Admin) return true;

        for (var producer: programEntity.getProducer()) {
            if (producer.getId() == currentUser.getId())
            {
                return true;
            }
        }

        return false;
    }

}
