package com.example.futudoll.retrofit;

import com.example.futudoll.todo.todoapp.model.SubTODO;

import java.util.List;

public class RequestTodoDTO {
    private String title;
    private String description;
    private int id = 0;
    private int parentIDi = 0;

    public RequestTodoDTO(String title, String description, int id, int parentIDi) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.parentIDi = parentIDi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentIDi() {
        return parentIDi;
    }

    public void setParentIDi(int parentIDi) {
        this.parentIDi = parentIDi;
    }
}
