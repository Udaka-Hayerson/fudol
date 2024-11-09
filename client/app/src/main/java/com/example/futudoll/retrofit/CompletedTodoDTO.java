package com.example.futudoll.retrofit;

public class CompletedTodoDTO {
    private int id;
    private boolean isCompleted;

    public CompletedTodoDTO(int id, boolean isCompleted) {
        this.id = id;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
