package com.example.futudoll.todo.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.futudoll.R;

public class SubDataEnterDialog extends AppCompatDialogFragment {
    private EditText editSubToDoTitle;
    private EditText editSubToDoDescription;
    SubDataEnterDialogListener subdialoglistener ;
    private final int position;
    private final String parentTitle;
    public SubDataEnterDialog(int position,  String parentTitle) {
        super();
        this.position = position;
        this.parentTitle = parentTitle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog_builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_sub_dialog, null);
        editSubToDoTitle = view.findViewById(R.id.edit_sub_todo_title);
        editSubToDoDescription = view.findViewById(R.id.edit_sub_todo_description);
        dialog_builder
                .setView(view)
                .setTitle("add sub tasks to " + parentTitle)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(), "addition sub tasks canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sub_todo_title = editSubToDoTitle.getText().toString();
                        String sub_todo_description = editSubToDoDescription.getText().toString();
                        subdialoglistener.applySubTexts( sub_todo_title, sub_todo_description, position);
                    }
                });
        return dialog_builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            subdialoglistener = (SubDataEnterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement SubDataEnterDialogListener");
        }
    }

    public  interface SubDataEnterDialogListener{
        void applySubTexts(String sub_todo_title, String sub_todo_description, int position);
    }
}
