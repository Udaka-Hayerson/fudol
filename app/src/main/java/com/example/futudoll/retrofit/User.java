package com.example.futudoll.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("Id")
    private int id;
    @SerializedName("Nickname")
    private String nickname;
    @SerializedName("Birthday")
    private String birthday;
    @SerializedName("Login")
    private String login;
    @SerializedName("Password")
    private String password;
    @SerializedName("Token")
    private String token;
    @SerializedName("Expected_salary")
    private double expected_salary;

    public User(int id, String nickname, String birthday, String login, String password, String token, double expected_salary) {
        this.id = id;
        this.nickname = nickname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.token = token;
        this.expected_salary = expected_salary;
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

    public double getExpected_salary() {
        return expected_salary;
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

    public void setExpected_salary(double expected_salary) {
        this.expected_salary = expected_salary;
    }
}