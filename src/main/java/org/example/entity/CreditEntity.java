package org.example.entity;

public class CreditEntity {
    private long programId;
    private UserEntity actor;

    public CreditEntity(long programId, UserEntity actor) {
        this.programId = programId;
        this.actor = actor;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public UserEntity getActor() {
        return actor;
    }

    public void setActor(UserEntity actor) {
        this.actor = actor;
    }

}
