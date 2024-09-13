package com.example.futudoll.retrofit;

public class User {
    int id;
    String nickname;
    String birthday;
    String login;
    String password;
    String token;
    double expected_salary;

    public User(int id, String nickname, String birthday, String login, String password, String token, double expected_salary) {
        this.id = id;
        this.nickname = nickname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.token = token;
        this.expected_salary = expected_salary;
    }
}