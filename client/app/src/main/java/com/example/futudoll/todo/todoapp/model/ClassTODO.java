package com.example.futudoll.todo.todoapp.model;

import java.util.List;
import java.util.Objects;

public class ClassTODO {

    private String title;
    private String description;
    private List<ClassTODO> subTODOList; // ttodolist
    private boolean completeTODO;
    private int id = 0; // todo: генерація id через hashCode
    private Integer parentID; //todo: зробить одну модель з ClassTODO і SubTODO

    public ClassTODO(String title, String description, List<ClassTODO> subTODOList, boolean completeTODO, Integer parentID) {
        this.title = title;
        this.description = description;
        this.subTODOList = subTODOList;
        this.completeTODO = completeTODO;
        this.id = hashCode();
        this.parentID = parentID;

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

    public List<ClassTODO> getSubTODOList() {
        return subTODOList;
    }

    public void setSubTODOList(List<ClassTODO> subTODOList) {
        this.subTODOList = subTODOList;
    }

    public boolean isCompleteTODO() {
        return completeTODO;
    }

    public void setCompleteTODO(boolean completeTODO) {
        this.completeTODO = completeTODO;
    }

    public Integer getParent_id() {
        return parentID;
    }

    public void setParent_id(Integer parentID) {
        this.parentID = parentID;
    }

    @Override
    public String toString() {
        return "ClassTODO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", subTODOList=" + subTODOList +
                ", completeTODO=" + completeTODO +
                ", id=" + id +
                ", parent_id=" + parentID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTODO classTODO = (ClassTODO) o;
        return completeTODO == classTODO.completeTODO && id == classTODO.id && parentID == classTODO.parentID &&
                Objects.equals(title, classTODO.title) && Objects.equals(description, classTODO.description) &&
                Objects.equals(subTODOList, classTODO.subTODOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, subTODOList, completeTODO, id, parentID);
    }
}

