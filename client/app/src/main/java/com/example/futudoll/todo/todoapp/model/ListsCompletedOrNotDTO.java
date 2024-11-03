package com.example.futudoll.todo.todoapp.model;

import java.util.List;

public class ListsCompletedOrNotDTO {
    private List<ClassTODO> completedToDo;
    private List<ClassTODO> notCompletedToDo;
    private List<SubTODO> completedSubToDo;
    private List<SubTODO> notCompletedSubToDo;

    public ListsCompletedOrNotDTO(List<ClassTODO> completedToDo, List<ClassTODO> notCompletedToDo,
                                  List<SubTODO> completedSubToDo, List<SubTODO> notCompletedSubToDo) {
        this.completedToDo = completedToDo;
        this.notCompletedToDo = notCompletedToDo;
        this.completedSubToDo = completedSubToDo;
        this.notCompletedSubToDo = notCompletedSubToDo;


    }

    public List<ClassTODO> getCompletedToDo() {
        return completedToDo;
    }

    public void setCompletedToDo(List<ClassTODO> completedToDo) {
        this.completedToDo = completedToDo;
    }

    public List<ClassTODO> getNotCompletedToDo() {
        return notCompletedToDo;
    }

    public void setNotCompletedToDo(List<ClassTODO> notCompletedToDo) {
        this.notCompletedToDo = notCompletedToDo;
    }

    public List<SubTODO> getCompletedSubToDo() {
        return completedSubToDo;
    }

    public void setCompletedSubToDo(List<SubTODO> completedSubToDo) {
        this.completedSubToDo = completedSubToDo;
    }

    public List<SubTODO> getNotCompletedSubToDo() {
        return notCompletedSubToDo;
    }

    public void setNotCompletedSubToDo(List<SubTODO> notCompletedSubToDo) {
        this.notCompletedSubToDo = notCompletedSubToDo;
    }
}
