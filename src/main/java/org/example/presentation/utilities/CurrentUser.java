package org.example.presentation.utilities;

import org.example.domain.buisnessComponents.Program;
import org.example.domain.buisnessComponents.Role;
import org.example.domain.buisnessComponents.User;

public class CurrentUser {

    private static CurrentUser single_instance = null;
    private User currentUser;

    public static CurrentUser getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentUser();

        return single_instance;
    }

    public User getUser() {
        return currentUser;
    }

    public void init(User userEntity) {
        this.currentUser = userEntity;
    }

    public boolean gotAccessToProgram(Program programEntity)
    {
        if (currentUser.getRole() == Role.Admin) return true;

        if (programEntity == null) return false;
        if (programEntity.getProducers() == null) return false;

        for (var producer: programEntity.getProducers()) {
            if (producer.getId() == currentUser.getId())
            {
                return true;
            }
        }

        return false;
    }

}
