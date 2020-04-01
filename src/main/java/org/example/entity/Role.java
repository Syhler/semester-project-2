package org.example.entity;

public enum Role {
    Admin(1),
    Manufacture(2),
    Producer(3),
    Actor(4);

    public final int roleValue;

    Role(int roleValue) {
        this.roleValue = roleValue;
    }

    public int getValue() {
        return this.roleValue;
    }
}
