package com.example.futudoll.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("token")
    private String token;
    @SerializedName("expectedSalary")
    private double expectedSalary;
    @SerializedName("timeCount")
    private int timeCount;

    public User(String id, String nickname, String birthday, String token, double expectedSalary, int timeCount) {
        this.id = id;
        this.nickname = nickname;
        this.birthday = birthday;
        this.token = token;
        this.expectedSalary = expectedSalary;
        this.timeCount = timeCount;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getToken() {
        return token;
    }

    public double getExpectedSalary() {
        return expectedSalary;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpectedSalary(double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

}