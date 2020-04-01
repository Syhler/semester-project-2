package org.example;

import org.example.entity.UserEntity;

public class CurrentUser {

    private static CurrentUser single_instance = null;
    private UserEntity currentUser;

    public UserEntity getUserEntity() {
        return currentUser;
    }

    public void init(UserEntity userentity) {
        this.currentUser = userentity;
    }

    public static CurrentUser getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentUser();

        return single_instance;
    }
}
