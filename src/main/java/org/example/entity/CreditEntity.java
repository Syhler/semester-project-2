package org.example.entity;

public class CreditEntity {
    private String programId;
    private UserEntity actor;

    public CreditEntity(String programId, UserEntity actor) {
        this.programId = programId;
        this.actor = actor;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public UserEntity getActor() {
        return actor;
    }

    public void setActor(UserEntity actor) {
        this.actor = actor;
    }
}
