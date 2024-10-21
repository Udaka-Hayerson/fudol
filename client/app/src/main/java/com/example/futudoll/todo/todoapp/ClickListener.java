package com.example.futudoll.todo.todoapp;

public interface ClickListener {
    void onCreateClick(int position);
    void onClickTitle(int position);
    void onTouchTitle(int position);
    void onClickDescription(int position, int sub_position);
    void onClickSubTitle(int position, int sub_position);
    void onTouchSubTitle(int position, int sub_position);
}