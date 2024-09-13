package com.example.futudoll.retrofit;

public class AuthRequest {
    String nickname;
    String birthday;
    String login;
    String password;
    double expected_salary;

    public AuthRequest(String nickname, String birthday, String login, String password, double expected_salary) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.expected_salary = expected_salary;
    }
}


