package org.example.domain;

import java.util.List;

public class Name
{
    private String firstName;
    private List<String> middleNames;
    private String lastName;

    public Name(String firstName, List<String> middleNames, String lastName) {
        this.firstName = firstName;
        this.middleNames = middleNames;
        this.lastName = lastName;
    }

    public String getFirstMiddleName() {
        if (!middleNames.isEmpty())
        {
            return middleNames.get(0);
        }
        return null;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
