package com.example.futudoll.todo.todoapp;

import android.content.Context;
import android.util.Log;

import com.example.futudoll.todo.todoapp.model.ClassTODO;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

class JSONHelper {

    private static final String FILE_NAME = "data_todo.json";

    static boolean exportToJSON(Context context, List<ClassTODO> dataList) {

//        Toast.makeText(context, (CharSequence) context.getFilesDir(), Toast.LENGTH_LONG).show();
        Log.i("app" , String.valueOf(context.getFilesDir())); //* /data/user/0/com.example.todoapp/files
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setUsers(dataList);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    static List<ClassTODO> importFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getUsers();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    private static class DataItems {
        private List<ClassTODO> todos;

        List<ClassTODO> getUsers() {
            return todos;
        }
        void setUsers(List<ClassTODO> todos) {
            this.todos = todos;
        }
    }
}