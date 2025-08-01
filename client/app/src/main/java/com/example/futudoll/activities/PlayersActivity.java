package com.example.futudoll.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.futudoll.R;
import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.UserChallenge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    MainApi mainApi;
    LayoutInflater layoutInflater;
    List<UserChallenge> users;
    PlayersAdapter playersAdapter;
    ListView listView;
    SharedPreferences sharedPreferences;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.list);
        mainApi = Utils.retrofitInstance(this);

        layoutInflater = getLayoutInflater();
        Call<List<UserChallenge>> call = mainApi.getUsers("Bearer " + sharedPreferences.getString("token", "loh"));
        call.enqueue(new Callback<List<UserChallenge>>() {
            @Override
            public void onResponse(Call<List<UserChallenge>> call, Response<List<UserChallenge>> response){
                if(response.isSuccessful()){
                    Log.e(LOG_TAG, " menu user " + response.body());
                    users = response.body(); // todo
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
            public void onFailure(Call<List<UserChallenge>> call, Throwable t) {
                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backMenuBack(View view) {
        Log.e(LOG_TAG, "back menu");
        Intent intentMenu = new Intent(this, MenuActivity.class);
        intentMenu.putExtra("token", sharedPreferences.getString("token", "loh"));
        startActivity(intentMenu);
    }

    private List<UserChallenge> sortUsersByTimeCount(List<UserChallenge> users) { //todo:
        Log.e(LOG_TAG, "users " + users);
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
                    UserChallenge user = users.get(i);
                    users.set(i, users.get(i + 1));
                    users.set(i + 1, user);
                }
            }
        }

        return users;
    }
}
