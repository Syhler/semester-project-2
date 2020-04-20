package org.example.presentation.utilities;

import org.example.domain.Program;
import org.example.domain.Role;
import org.example.domain.User;

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

        for (var producer: programEntity.getProducers()) {
            if (producer.getId() == currentUser.getId())
            {
                return true;
            }
        }

        return false;
    }

}
