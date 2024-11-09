package com.example.futudoll.todo.todoapp.model;

import java.util.List;
import java.util.Objects;

public class ClassTODO {

    private String title;
    private String description;
    private List<SubTODO> subTODOList;
    private boolean completeTODO;
    static int count = 0;
    private int id = 0; // todo: генерація id через hashCode
//    private int parent_id; todo: зробить одну модель з ClassTODO і SubTODO

    public ClassTODO(String title, String description, List<SubTODO> subTODOList, boolean completeTODO) {
        this.title = title;
        this.description = description;
        this.subTODOList = subTODOList;
        this.completeTODO = completeTODO;
        this.id = count;
        count++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<SubTODO> getSubTODOList() {
        return subTODOList;
    }

    public void setSubTODOList(List<SubTODO> subTODOList) {
        this.subTODOList = subTODOList;
    }

    public boolean isCompleteTODO() {
        return completeTODO;
    }

    public void setCompleteTODO(boolean completeTODO) {
        this.completeTODO = completeTODO;
    }


//    public boolean isNew_user() {
//        return new_user;
//    }
//
//    public void setNew_user(boolean new_user) {
//        this.new_user = new_user;
//    }

    @Override
    public String toString() {
        return "ClassTODO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", subTODOList=" + subTODOList +
                ", completeTODO=" + completeTODO +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTODO classTODO = (ClassTODO) o;
        return completeTODO == classTODO.completeTODO && id == classTODO.id && title.equals(classTODO.title) && description.equals(classTODO.description) && subTODOList.equals(classTODO.subTODOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, subTODOList, completeTODO, id);
    }
}

