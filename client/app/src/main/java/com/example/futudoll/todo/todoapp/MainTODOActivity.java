package com.example.futudoll.todo.todoapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.futudoll.R;
import com.example.futudoll.activities.MenuActivity;
import com.example.futudoll.activities.Utils;
import com.example.futudoll.retrofit.CompletedTodoDTO;
import com.example.futudoll.retrofit.DeleteTodoDTO;
import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.RequestTodoDTO;
import com.example.futudoll.todo.todoapp.adapter.TODOAdapter;
import com.example.futudoll.todo.todoapp.model.ClassTODO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainTODOActivity
        extends AppCompatActivity
        implements DataEnterDialog.DataEnterDialogListener,
        SubDataEnterDialog.SubDataEnterDialogListener,
        DeleteOrCompletedTODODialog.DeleteOrCompletedTODODialogListener,
        DeleteOrCompletedSubTODODialog.DeleteOrCompletedSubTODODialogListener {
    final String LOG_TAG = "myLogs";
    MainApi mainApi;
    RecyclerView TODORecycler;
    TODOAdapter todoAdapter;
    LinearLayoutManager manager;
    List<ClassTODO> TODOList = new ArrayList<>();
    ClickListener clickListener;
    SharedPreferences sharedPreferences;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_todo);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        token = "Bearer " + sharedPreferences.getString("token", "loh");
        TODORecycler = findViewById(R.id.recycler_todo);
        manager = new LinearLayoutManager(MainTODOActivity.this);
        clickListener = getAdapterClickListener();
        updateAdapter();

    }


    public void updateAdapter() {
        getTodoFromServer();
//        todoAdapter = new TODOAdapter(TODOList, this, clickListener);
//        TODORecycler.setAdapter(todoAdapter);
//        TODORecycler.setLayoutManager(manager);
//        todoAdapter.notifyDataSetChanged();
    }

    public void getTodoFromServer(){
        mainApi = Utils.retrofitInstance(this);
        Call<List<ClassTODO>> call = mainApi.getToDoLIst(token);
        call.enqueue(new Callback<List<ClassTODO>>() {
            @Override
            public void onResponse(Call<List<ClassTODO>> call, Response<List<ClassTODO>> response){
                if(response.isSuccessful() && response.body() != null){
                    Log.e(LOG_TAG, "todo list" + response.body());
                    TODOList = response.body();
                    todoAdapter = new TODOAdapter(TODOList, MainTODOActivity.this, clickListener);
                    TODORecycler.setAdapter(todoAdapter);
                    TODORecycler.setLayoutManager(manager);
                    todoAdapter.notifyDataSetChanged();


                }
                else {
                    Log.e(LOG_TAG, "Response error: " + response.message());
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<ClassTODO>> call, Throwable t) {
                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addTodoOnServer(String title, String description, int id, Integer parentID){
        RequestTodoDTO requestTodoDTO = new RequestTodoDTO(title, description, id, parentID);
        Call<ResponseBody> call = mainApi.addToDo(token, requestTodoDTO);
        callHandler(call);
    }

    public void deleteTodoFromServer(int id){
        DeleteTodoDTO deleteTodoDTO = new DeleteTodoDTO(id);
        Call<ResponseBody> call = mainApi.deleteToDo(token, deleteTodoDTO);
        callHandler(call);
    }

    public void completeTodoOnServer(int id){
        CompletedTodoDTO completedTodoDTO = new CompletedTodoDTO(id, true);
        Call<ResponseBody> call = mainApi.setCompletedState(token, completedTodoDTO);
        callHandler(call);
    }

    public void callHandler(Call<ResponseBody> call){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()  && response.body() != null) {
                    ResponseBody responseBody = response.body();
                    updateAdapter();
                    Log.d(LOG_TAG, String.valueOf(responseBody));
                    Log.d(LOG_TAG, "Request successful");

                } else {
                    Log.e(LOG_TAG, "Response error: " + response.code() + " " + response.message());
                    Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteTODO(int position) {
        deleteTodoFromServer(TODOList.get(position).getId());
        TODOList.remove(position);


    }

    public void deleteSubTODO(int position, int sub_todo_position) {
        deleteTodoFromServer(TODOList.get(position).getSubTODOList().get(sub_todo_position).getId());
        (TODOList.get(position).getSubTODOList()).remove(sub_todo_position);

    }

    public void completeTODO(int position) {
        completeTodoOnServer(TODOList.get(position).getId());
        Toast.makeText(this, TODOList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

    }

    public void completeSubTODO(int position, int sub_todo_position) {
        completeTodoOnServer((TODOList.get(position).getSubTODOList()).get(sub_todo_position).getId());
        Toast.makeText(this, (TODOList.get(position).getSubTODOList()).get(sub_todo_position).getTitle(), Toast.LENGTH_SHORT).show();

    }

    public void addToDo(View v) {
        openCreateDialog();
    }

    public void addSubToDo(int position) {
        openCreateSubDialog(position);
    }

    public void addNewTODO(String title, String description, List<ClassTODO> sub_todo_list) {
        //TODO: get API TODOList
        List<ClassTODO> todo_list = TODOList;
        todo_list.add(new ClassTODO(title, description, new ArrayList<ClassTODO>(), false, 0));
        TODOList = todo_list;
        addTodoOnServer(title, description, todo_list.get(todo_list.size()-1).getId(), 0); // todo : getID()
        updateAdapter();
    }

    public void addNewSubTODO(String title, String description, int position) {
        //TODO: get API subTODOList
        List<ClassTODO> sub_todo_list = (TODOList.get(position)).getSubTODOList();
        sub_todo_list.add(new ClassTODO(title, description, null, false, (TODOList.get(position)).getId()));
        (TODOList.get(position)).setSubTODOList(sub_todo_list);
        addTodoOnServer(title, description, sub_todo_list.get(sub_todo_list.size() - 1).getId() , TODOList.get(position).getId()); // todo : getID()
        updateAdapter();

    }

    @Override
    public void applyTexts(String todo_title, String todo_description) {
        addNewTODO(todo_title, todo_description, new ArrayList<ClassTODO>());
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


    public void openCreateDialog() {
        DataEnterDialog dialog = new DataEnterDialog();
        dialog.show(getSupportFragmentManager(), "Data Enter Dialog");
    }

    public void openCreateSubDialog(int position) {
        SubDataEnterDialog subDialog = new SubDataEnterDialog(position, TODOList.get(position).getTitle());
        subDialog.show(getSupportFragmentManager(), "Sub Data Enter Dialog");
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











