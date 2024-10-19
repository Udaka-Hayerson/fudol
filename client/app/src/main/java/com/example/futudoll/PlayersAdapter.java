package com.example.futudoll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.futudoll.retrofit.User;

import java.util.List;

public class PlayersAdapter extends BaseAdapter {

    private int place = 1;
    private List<User> users;
    private LayoutInflater layoutInflater;

    public PlayersAdapter(List<User> users, LayoutInflater layoutInflater) {
        this.users = users;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.player_item, parent, false);
        }
        User user = (User) getItem(position);

        TextView nickname = (TextView) view.findViewById(R.id.nickname);
        TextView time_count = (TextView) view.findViewById(R.id.time_count);


        nickname.setText(place + " " + user.getNickname());
        time_count.setText("score: " + user.getTimeCount());
        place++;

        return view;
    }
}