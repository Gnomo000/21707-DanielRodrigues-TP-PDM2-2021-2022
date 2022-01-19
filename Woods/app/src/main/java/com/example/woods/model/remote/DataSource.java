package com.example.woods.model.remote;

import com.example.woods.model.Woods;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSource {
    private static final String BASE_URL = "https://my-json-server.typicode.com/Gnomo000/21707-DanielRodrigues-TP-PDM2-2021-2022/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static WoodsService service;

    public static WoodsService getService() {
        if (service == null) {
            service = retrofit.create(WoodsService.class);
        }
        return service;
    }
}
