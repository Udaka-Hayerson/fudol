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
    @SerializedName("expectedSalary")
    private double expectedSalary;

    public User(String id, String nickname, String birthday, double expectedSalary) {
        this.id = id;
        this.nickname = nickname;
        this.birthday = birthday;
        this.expectedSalary = expectedSalary;
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

    public double getExpectedSalary() {
        return expectedSalary;
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

    public void setExpectedSalary(double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }


}