package com.jcardenas.example.minim2_qt2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcardenas.example.minim2_qt2020.models.*;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ClientInfoStatus;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://api.github.com/";
    private GithubAPI APIinterface;
    private ProgressBar progressBar;
    private String user;

    private RecyclerView recyclerView;
    private TextView followersTextView;
    private TextView followingTextView;
    private android.widget.ImageView ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.repos);
        ImageView = findViewById(R.id.avatarImageView);
        followersTextView = findViewById(R.id.followersTextView);
        followingTextView = findViewById(R.id.followingTextView);

        //Start retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIinterface = retrofit.create(GithubAPI.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        loadInfo();
    }

    public void loadInfo(){
        Log.d("user", user);
        Call<User> call = APIinterface.infoUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User user = response.body();
                Log.d("following i followers", String.valueOf(user.getFollowing()) + ", " +String.valueOf(user.getFollowers()));
                followingTextView.setText(String.valueOf(user.getFollowing()));
                followersTextView.setText(String.valueOf(user.getFollowers()));
                Picasso.with(getApplicationContext()).load(user.getAvatar_url()).into(ImageView);
                showProgress(false);

                loadRepos();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Code: " + t.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        });
    }

    public void loadRepos(){
        showProgress(true);
        Call<List<Repo>> call = APIinterface.listaRepos(user);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                if(response.isSuccessful()){
                    Log.d("onResponse", "lsita ha llegado");
                    List<Repo> listaRepos = response.body();
                    recyclerView = findViewById(R.id.repos);

                    RVAdapter myAdapter = new RVAdapter(getApplicationContext(), listaRepos);
                    recyclerView.setAdapter(myAdapter);
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    public void showProgress (boolean visible){
        //Sets the visibility/invisibility of progressBar
        if(visible)
            this.progressBar.setVisibility(View.VISIBLE);
        else
            this.progressBar.setVisibility(View.GONE);
    }
}