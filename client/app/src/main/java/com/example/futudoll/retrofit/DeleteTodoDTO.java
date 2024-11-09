package com.example.futudoll.retrofit;

public class DeleteTodoDTO {
    private String id ;

    public DeleteTodoDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
