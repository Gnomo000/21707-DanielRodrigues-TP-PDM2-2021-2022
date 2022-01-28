package com.example.woods.model.remote;

import com.example.woods.model.Orders;
import com.example.woods.model.User;
import com.example.woods.model.Woods;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WoodsService {
    @GET("users/")
    Call<List<User>> getUserByEmailAndPassword(@Query(value = "email", encoded = true) String email, @Query(value = "password", encoded = true) String password);

    @GET("users/")
    Call<List<User>> getUserByEmail(@Query(value = "email", encoded = true) String email);

    @POST("users/")
    Call<User> addUser(@Body User user);

    @GET("woods/")
    Call<List<Woods>> getAllWoods();

    @GET("orders/")
    Call<List<Orders>> getOrderById(@Query(value = "users_id", encoded = true) int users_id);

    @POST("users/")
    Call<Orders> addOrder(@Body Orders orders);

    @PUT("users/{id}/")
    Call<User> updateUser(@Path("id") int id,@Body User user);
}
