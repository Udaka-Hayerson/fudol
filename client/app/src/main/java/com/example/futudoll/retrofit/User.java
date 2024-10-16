package com.example.futudoll.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;
    @SerializedName("expectedSalary")
    private double expectedSalary;
    @SerializedName("timeCount")
    private int timeCount;

    public User(int id, String nickname, String birthday, String login, String password, String token, double expectedSalary, int timeCount) {
        this.id = id;
        this.nickname = nickname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.token = token;
        this.expectedSalary = expectedSalary;
        this.timeCount = timeCount;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public double getExpectedSalary() {
        return expectedSalary;
    }

    public double getTimeCount() {
        return timeCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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