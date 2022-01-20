package com.example.woods.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.woods.model.local.AppDatabase;
import com.example.woods.model.local.OrdersDao;
import com.example.woods.model.local.UserDao;
import com.example.woods.model.local.WoodsDao;
import com.example.woods.model.remote.DataSource;
import com.example.woods.model.remote.WoodsService;

import java.util.List;

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

    public LiveData<Users> getUser(String email, String password){
        this.updateUser(email,password);
        return this.usersDao.getUserByEmailAndPassword(email,password);
    }

    public void deleteUsers(){
        usersDao.delete();
    }

    public void updateUser(String email, String password) {
        WoodsService service = DataSource.getService();
        Call<List<Users>> call = service.getUserByEmailAndPassword(email,password);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    List<Users> users = response.body();
                    if (users.size() > 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (Users users:
                                     users) {
                                    usersDao.add(users);
                                }
                            }
                        }).start();
                    }
                }else {

                }

            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void addUser(Users user) {
        WoodsService service = DataSource.getService();
        Call<Users> call = service.addUser(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    Users users = response.body();
                    usersDao.add(users);
                }else {

                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
