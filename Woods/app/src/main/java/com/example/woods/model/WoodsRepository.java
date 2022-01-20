package com.example.woods.model;

import android.content.Context;

import com.example.woods.model.local.AppDatabase;
import com.example.woods.model.local.OrdersDao;
import com.example.woods.model.local.UserDao;
import com.example.woods.model.local.WoodsDao;
import com.example.woods.model.remote.DataSource;
import com.example.woods.model.remote.WoodsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoodsRepository {

    private final Context context;
    private WoodsDao woodsDao;
    private OrdersDao ordersDao;
    private UserDao usersDao;
    private Users finalUsers;


    public WoodsRepository(Context context) {
        this.woodsDao = AppDatabase.getInstance(context).getWoodsDao();
        this.ordersDao = AppDatabase.getInstance(context).getOrdersDao();
        this.usersDao = AppDatabase.getInstance(context).getUsersDao();
        this.context = context;
    }

    public Users getUser(String email, String password) {
        WoodsService service = DataSource.getService();
        Call<Users> call = service.getUserByEmailAndPassword(email,password);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    Users users = response.body();
                    finalUsers = users;
                    usersDao.add(users);
                }else {

                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return finalUsers;
    }

}
