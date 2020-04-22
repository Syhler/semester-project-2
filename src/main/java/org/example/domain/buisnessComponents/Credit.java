package org.example.domain.buisnessComponents;

public class Credit
{
    private User user;
    private long programId;

    public Credit(long programId, User user) {
        this.programId = programId;
        this.user = user;
    }

    public Credit(User user) {
        this.user = user;
    }

    public long getProgramId() {
        return programId;
    }

    public User getUser() {
        return user;
    }
}
