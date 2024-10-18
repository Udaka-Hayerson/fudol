package com.example.futudoll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    MainApi mainApi;
    LayoutInflater layoutInflater;
    List<User> users;
    PlayersAdapter playersAdapter;
    ListView listView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.list);
        mainApi = Utils.retrofitInstance(this);

        layoutInflater = getLayoutInflater();
        Call<List<User>> call = mainApi.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response){
                if(response.isSuccessful()){
                    Log.e(LOG_TAG, " menu user " + response.body());
                    users = response.body();
                    users = sortUsersByTimeCount(users);
                    playersAdapter = new PlayersAdapter(users, layoutInflater);
                    listView.setAdapter(playersAdapter);
                }
                else {
                    // Handle unsuccessful response
                    Log.e(LOG_TAG, "Response error: " + response.message());
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backMenuBack(View view) {
        Intent intentTimer = new Intent(this, MenuActivity.class);
        intentTimer.putExtra("token", sharedPreferences.getString("token", "loh"));
        startActivity(intentTimer);
    }

    private List<User> sortUsersByTimeCount(List<User> users) {
        int next_count = 0;
        int count = 0;
        for (int j = 0; j < users.size(); j++) {
            for (int i = 0; i < users.size(); i++) {
                count = (users.get(i)).getTimeCount();
                if(i == users.size() - 1) {
                    break;
                } else {
                    next_count = (users.get(i + 1)).getTimeCount();
                }
                if(next_count > count){
                    User user = users.get(i);
                    users.set(i, users.get(i + 1));
                    users.set(i + 1, user);
                }
            }
        }

        return users;
    }
}
