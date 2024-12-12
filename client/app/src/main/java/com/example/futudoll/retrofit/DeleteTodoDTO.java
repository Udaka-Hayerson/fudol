package com.example.futudoll.retrofit;

public class DeleteTodoDTO {
    private int id ;

    public DeleteTodoDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
