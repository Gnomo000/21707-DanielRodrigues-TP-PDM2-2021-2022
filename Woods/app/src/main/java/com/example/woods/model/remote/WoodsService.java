package com.example.woods.model.remote;

import com.example.woods.model.Users;
import com.example.woods.model.Woods;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WoodsService {
    @GET("users/")
    Call<List<Users>> getUserByEmailAndPassword(@Query(value = "email", encoded = true) String email, @Query(value = "password", encoded = true) String password);

    @POST("users/")
    Call<Users> addUser(@Body Users users);
}
