package org.example.persistence.entities;

public class CreditEntity
{

    private long programId;
    private UserEntity actor;

    public CreditEntity(long programId, UserEntity actor) {
        this.programId = programId;
        this.actor = actor;
    }

    public long getProgramId() {
        return programId;
    }

    public UserEntity getActor() {
        return actor;
    }
}
