package com.example.futudoll.todo.todoapp.model;

public class SubTODO {

    private String sub_title;
    private String sub_description;
    private boolean completeSubTODO;
    static int count = 0;
    private int sub_id;
//    int parent_id; TODO:

    public SubTODO( String sub_title, String sub_description, boolean completeSubTODO) {
        this.sub_title = sub_title;
        this.sub_description = sub_description;
        this.completeSubTODO = completeSubTODO;
        this.sub_id = count;
        count++;
    }

    public String getSubTitle() {
        return sub_title;
    }

    public void setSubTitle(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getSubDescription() {
        return sub_description;
    }

    public void setSubDescription(String sub_description) {
        this.sub_description = sub_description;
    }

    public boolean isCompleteSubTODO() {
        return completeSubTODO;
    }

    public void setCompleteSubTODO(boolean completeSubTODO) {
        this.completeSubTODO = completeSubTODO;
    }

    public int getSubId() {
        return sub_id;
    }

    public void setSubId(int sub_id) {
        this.sub_id = sub_id;
    }



}