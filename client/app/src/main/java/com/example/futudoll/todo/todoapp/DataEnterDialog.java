package com.example.futudoll.todo.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.futudoll.R;

public class DataEnterDialog extends AppCompatDialogFragment {
    private EditText editTODO_Title;
    private EditText editTODO_Description;
    DataEnterDialogListener dialoglistener ;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog_builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        editTODO_Title = view.findViewById(R.id.editTODOTitle);
        editTODO_Description = view.findViewById(R.id.editTODODescription);
        dialog_builder.setView(view)
                .setTitle("add new TODO")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String todo_title = editTODO_Title.getText().toString();
                        String todo_description = editTODO_Description.getText().toString();
                        dialoglistener.applyTexts(todo_title, todo_description);
                    }
                });
        return dialog_builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialoglistener = (DataEnterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DataEnterDialogListener");
        }
    }

    public  interface DataEnterDialogListener{
        void applyTexts(String todo_title, String todo_description);
    }
}
