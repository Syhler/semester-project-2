package org.example.presentation;

import org.example.entity.UserEntity;

public class CurrentUser {

    private static CurrentUser single_instance = null;
    private UserEntity currentUser;

    public UserEntity getUserEntity() {
        return currentUser;
    }

    public void init(UserEntity userEntity) {
        this.currentUser = userEntity;
    }

    public static CurrentUser getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentUser();

        return single_instance;
    }
}
