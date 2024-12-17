package com.example.futudoll.retrofit;

import java.util.List;

public class RequestTodoDTO {
    private String title;
    private String description;
    private int id = 0;
    private Integer parentID = 0;

    public RequestTodoDTO(String title, String description, int id, Integer parentID) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.parentID = parentID;
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

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }
}
