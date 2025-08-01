package com.example.futudoll.retrofit;

public class UserChallenge {
    private int timeCount;
    private UserPreview user;

    public UserChallenge(int timeCount, UserPreview user) {
        this.timeCount = timeCount;
        this.user = user;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public UserPreview getUser() {
        return user;
    }

    public void setUser(UserPreview user) {
        this.user = user;
    }
}
