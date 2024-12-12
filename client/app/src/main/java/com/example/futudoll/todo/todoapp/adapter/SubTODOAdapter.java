package com.example.futudoll.todo.todoapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futudoll.R;
import com.example.futudoll.todo.todoapp.ClickListener;
import com.example.futudoll.todo.todoapp.model.ClassTODO;

import java.util.List;

public class SubTODOAdapter extends RecyclerView.Adapter<SubTODOAdapter.SubTODOViewHolder>{
    List<ClassTODO> sub_todos;
    ClickListener sub_listener;
    public SubTODOAdapter( List<ClassTODO> sub_todos, ClickListener sub_listener) {
        this.sub_todos = sub_todos;
        this.sub_listener = sub_listener;
    }
    @NonNull
    @Override
    public SubTODOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View subTODOItems = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_todo_item, parent, false);
        return new SubTODOViewHolder(subTODOItems);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTODOViewHolder holder, int position) {
        ClassTODO subTODO = sub_todos.get(position);
        holder.subTodoTitle.setText(subTODO.getTitle());
        holder.descriptionTodo.setText(subTODO.getDescription());
    }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickSubTitle(subTODO.getSubId());
//                Toast.makeText(holder.itemView.getContext(),"SubTODOAdapter - \n А А МОГЛА БИ БУТИ ВАША РЕКЛАМА", Toast.LENGTH_SHORT).show();
//            }
//        });
//    public void onClickSubTitle(int id){
//        SubTODO todo = sub_todos.get(id);
//        String title = todo.getSubTitle();
//        String desc = todo.getSubDescription();
//        Intent intent = new Intent();
//        intent.putExtra("title", title);
//        intent.putExtra("desc", desc);
//    }

    @Override
    public int getItemCount() {
        return sub_todos.size();
    }

    public class SubTODOViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subTodoTitle, descriptionTodo;
        @SuppressLint("ClickableViewAccessibility")
        public SubTODOViewHolder(@NonNull View itemView) {
            super(itemView);
            subTodoTitle = itemView.findViewById(R.id.sub_todo_title);
            descriptionTodo = itemView.findViewById(R.id.description_sub_todo);
            subTodoTitle.setOnClickListener(this);
            descriptionTodo.setOnClickListener(this);
            subTodoTitle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        sub_listener.onTouchSubTitle(getAbsoluteAdapterPosition(), getBindingAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.sub_todo_title) {
                sub_listener.onClickSubTitle(getAbsoluteAdapterPosition(), getBindingAdapterPosition());
            } else if(v.getId() == R.id.description_sub_todo){
                sub_listener.onClickDescription(getAbsoluteAdapterPosition(), getBindingAdapterPosition());
//                listener.onClickTitle(getAdapterPosition());-------- deprecated
            }

        }
    }
}
