package com.example.futudoll.todo.todoapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futudoll.R;
import com.example.futudoll.todo.todoapp.ClickListener;
import com.example.futudoll.todo.todoapp.model.ClassTODO;
import com.example.futudoll.todo.todoapp.model.SubTODO;

import java.util.ArrayList;
import java.util.List;

public class TODOAdapter extends RecyclerView.Adapter<TODOAdapter.TODOViewHolder> {
    // notifyDataSetChanged();
    List<SubTODO> subTODOList = new ArrayList<>();
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<ClassTODO> todos;
    Context context;
    ClickListener listener;

    public TODOAdapter(List<ClassTODO> todos, Context context, ClickListener listener) {
        this.todos = todos;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public TODOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View TODOItems = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TODOViewHolder(TODOItems);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull TODOViewHolder holder, int i) {
        ClassTODO item = todos.get(i);
        holder.todoTitle.setText(item.getTitle());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recycleerSubTodo.getContext(), LinearLayoutManager.HORIZONTAL, false);

        layoutManager.setInitialPrefetchItemCount(item.getSubTODOList().size());//edit
        subTODOList = item.getSubTODOList();
        SubTODOAdapter subTODOAdapter = new SubTODOAdapter(subTODOList, listener);// виклик адаптеру
        holder.recycleerSubTodo.setLayoutManager(layoutManager);
        holder.recycleerSubTodo.setAdapter(subTODOAdapter);
        holder.recycleerSubTodo.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class TODOViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView todoTitle;
        private final RecyclerView recycleerSubTodo;
        private final TextView createSubToDo;

        @SuppressLint("ClickableViewAccessibility")
        public TODOViewHolder(View itemView) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            recycleerSubTodo = itemView.findViewById(R.id.recycleer_sub_todo);
            createSubToDo = itemView.findViewById(R.id.sub_todo_add);
            createSubToDo.setOnClickListener(this::onClick);
            todoTitle.setOnClickListener(this::onClick);
            todoTitle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        listener.onTouchTitle(getAbsoluteAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.sub_todo_add) {
                listener.onCreateClick(getAbsoluteAdapterPosition());
            } else if(v.getId() == R.id.todo_title){
                listener.onClickTitle(getAbsoluteAdapterPosition());
//                listener.onClickTitle(getAdapterPosition());-------- deprecated
            }
        }
    }

}