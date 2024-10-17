package com.example.futudoll.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IncreaseTimeCountDTO implements Serializable {
    @SerializedName("count")
    private int count;

    public IncreaseTimeCountDTO(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
