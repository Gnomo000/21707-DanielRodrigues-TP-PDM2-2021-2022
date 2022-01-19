package com.example.woods.model.remote;

import com.example.woods.model.Woods;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface WoodsService {
    @GET("woods/")
    Call<List<Woods>> getAllWoods(@Header("Authorization") String authToken);
}
