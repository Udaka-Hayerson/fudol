package com.example.futudoll.retrofit;

public class AuthRequest {
    String nickname;
    String birthday;
    String login;
    String password;
    double expectedSalary;

    public AuthRequest(String nickname, String birthday, String login, String password, double expectedSalary) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.expectedSalary = expectedSalary;
    }
}


