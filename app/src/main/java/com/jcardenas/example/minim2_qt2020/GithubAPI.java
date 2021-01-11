package com.jcardenas.example.minim2_qt2020;

import com.jcardenas.example.minim2_qt2020.models.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubAPI {

    @GET("/users/{username}")
    Call<User> infoUser (@Path("username") String user);

    @GET("/users/{username}/repos")
    Call<List<Repo>> listaRepos(@Path("username") String user);
}
