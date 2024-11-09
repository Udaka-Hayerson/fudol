package com.example.futudoll.todo.todoapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.futudoll.R;
import com.example.futudoll.activities.MenuActivity;
import com.example.futudoll.activities.PlayersActivity;
import com.example.futudoll.todo.todoapp.adapter.TODOAdapter;
import com.example.futudoll.todo.todoapp.model.ClassTODO;
import com.example.futudoll.todo.todoapp.model.SubTODO;

import java.util.ArrayList;
import java.util.List;

public class MainTODOActivity
        extends AppCompatActivity
        implements DataEnterDialog.DataEnterDialogListener,
        SubDataEnterDialog.SubDataEnterDialogListener,
        DeleteOrCompletedTODODialog.DeleteOrCompletedTODODialogListener,
        DeleteOrCompletedSubTODODialog.DeleteOrCompletedSubTODODialogListener {
    RecyclerView TODORecycler;
    TODOAdapter todoAdapter;
    LinearLayoutManager manager;
    static List<ClassTODO> TODOList = new ArrayList<>();
    ClickListener clickListener;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_todo);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TODORecycler = findViewById(R.id.recycler_todo);
        manager = new LinearLayoutManager(MainTODOActivity.this);
        clickListener = getAdapterClickListener();

//        open();

        updateAdapter(); /* ==
        todoAdapter = new TODOAdapter(TODOList, this, clickListener);
        TODORecycler.setAdapter(todoAdapter);
        TODORecycler.setLayoutManager(manager); */

    }

    public void deleteTODO(int position) {
        TODOList.remove(position);
//        save();
        updateAdapter();

    }

    public void deleteSubTODO(int position, int sub_todo_position) {
        (TODOList.get(position).getSubTODOList()).remove(sub_todo_position);
//        save();
        updateAdapter();

    }

    public void completeTODO(int position) {
//        TODOList.get(position).getTitle();
        Toast.makeText(this, TODOList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        updateAdapter();

    }

    public void completeSubTODO(int position, int sub_todo_position) {
//        (TODOList.get(position).getSubTODOList()).get(sub_todo_position).getSubTitle();
        Toast.makeText(this, (TODOList.get(position).getSubTODOList()).get(sub_todo_position).getSubTitle(), Toast.LENGTH_SHORT).show();
        updateAdapter();

    }

    public void addToDo(View v) {
        openCreateDialog();
    }

    public void addSubToDo(int position) {
        openCreateSubDialog(position);
    }

    public void openCreateDialog() {
        DataEnterDialog dialog = new DataEnterDialog();
        dialog.show(getSupportFragmentManager(), "Data Enter Dialog");
    }

    public void openCreateSubDialog(int position) {
        SubDataEnterDialog subDialog = new SubDataEnterDialog(position);
        subDialog.show(getSupportFragmentManager(), "Sub Data Enter Dialog");
    }

    public void addNewTODO(String title, String description, List<SubTODO> sub_todo_list) {
        //TODO: get API TODOList
        List<ClassTODO> todo_list = MainTODOActivity.TODOList;
        todo_list.add(new ClassTODO(title, description, new ArrayList<SubTODO>(), false)); // todo_count
        MainTODOActivity.TODOList = todo_list;
//        save();
        updateAdapter();
    }

    public void addNewSubTODO(String title, String description, int position) {
        //TODO: get API subTODOList
        List<SubTODO> sub_todo_list = (TODOList.get(position)).getSubTODOList();
        sub_todo_list.add(new SubTODO(title, description, false, (TODOList.get(position)).getId()));
        (TODOList.get(position)).setSubTODOList(sub_todo_list);
//        save();
        updateAdapter();

    }

    public void updateAdapter() {
        // TODO: get list from API
        todoAdapter = new TODOAdapter(TODOList, this, clickListener);
        TODORecycler.setAdapter(todoAdapter);
        TODORecycler.setLayoutManager(manager);
//        todoAdapter.notifyDataSetChanged();
    }

    @Override
    public void applyTexts(String todo_title, String todo_description) {
        addNewTODO(todo_title, todo_description, new ArrayList<SubTODO>());
    }

    @Override
    public void applySubTexts(String sub_todo_title, String sub_todo_description, int position) {
        addNewSubTODO(sub_todo_title, sub_todo_description, position);
    }

    @Override
    public void deleteOrCompleteTODO(boolean delete, boolean complete, int position) {
        if (delete) deleteTODO(position);
        if (complete) completeTODO(position);
    }

    @Override
    public void deleteOrCompleteSubTODO(boolean delete, boolean complete, int position, int sub_todo_position) {
        if (delete) deleteSubTODO(position, sub_todo_position);
        if (complete) completeSubTODO(position, sub_todo_position);
    }

    public void backOnMenuFromTODO(View view) {
        Intent intentBack = new Intent(this, MenuActivity.class);
        intentBack.putExtra("token", sharedPreferences.getString("token", "loh"));
        startActivity(intentBack);
    }

    @NonNull
    private ClickListener getAdapterClickListener() {
        return new ClickListener() {
            @Override
            public void onCreateClick(int position) {
                addSubToDo(position);
            }

            @Override
            public void onClickTitle(int position) {
                Toast.makeText(MainTODOActivity.this, "intent TODOfullScreen", Toast.LENGTH_SHORT).show();
                // intent TODOfullScreen
            }

            @Override
            public void onTouchTitle(int position) {
                DeleteOrCompletedTODODialog dialog = new DeleteOrCompletedTODODialog();
                dialog.setPosition(position);
                dialog.show(getSupportFragmentManager(), "Delete Or Complete TODO Dialog");

            }

            @Override
            public void onClickDescription(int position, int sub_position) {
                Toast.makeText(MainTODOActivity.this, "onClickDescription", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickSubTitle(int position, int sub_position) {
                Toast.makeText(MainTODOActivity.this, "onClickSubTitle", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTouchSubTitle(int position, int sub_position) {
                DeleteOrCompletedSubTODODialog dialog = new DeleteOrCompletedSubTODODialog();
                dialog.setPosition(position);
                dialog.setSubPosition(sub_position);
                dialog.show(getSupportFragmentManager(), "Delete Or Complete TODO Dialog");
            }
        };
    }

}











