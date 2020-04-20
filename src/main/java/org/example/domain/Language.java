package org.example.domain;

public enum Language{


    Danish(1),

    English(2);


    public final int roleValue;

    Language(int roleValue) {
        this.roleValue = roleValue;
    }

    public int getValue() {
        return this.roleValue;
    }

    public static Language getLanguageById(int roleId)
    {
        for (var role : Language.values())
        {
            if (role.getValue() == roleId)
            {
                return role;
            }
        }

        return null;
    }
}
