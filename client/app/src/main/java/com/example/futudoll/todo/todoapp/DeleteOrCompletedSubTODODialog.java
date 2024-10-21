package com.example.futudoll.todo.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.futudoll.R;

public class DeleteOrCompletedSubTODODialog extends AppCompatDialogFragment {
    DeleteOrCompletedSubTODODialogListener dialogListener ;
    int todo_position = 0;
    int sub_todo_position = 0;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog_builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_delete_or_completed_dialog, null);

        dialog_builder.setView(view)
                .setTitle("Delete Or Complete SubTODO")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton("complete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.deleteOrCompleteSubTODO(false, true, todo_position, sub_todo_position);
                    }
                })
                .setNeutralButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.deleteOrCompleteSubTODO(true, false, todo_position, sub_todo_position);
                    }
                });
        return dialog_builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DeleteOrCompletedSubTODODialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DeleteOrCompletedDialogListener");
        }
    }
    public void setPosition(int todo_position) {
        this.todo_position = todo_position;
    }
    public void setSubPosition(int sub_todo_position) {
        this.sub_todo_position = todo_position;
    }

    public  interface DeleteOrCompletedSubTODODialogListener{
        void deleteOrCompleteSubTODO(boolean delete, boolean complete, int position, int sub_todo_position);
    }
}
