package com.example.futudoll.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserSignInDTO  implements Serializable {
    @SerializedName("login")
    String login;

    @SerializedName("password")
    String password;

    public UserSignInDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
