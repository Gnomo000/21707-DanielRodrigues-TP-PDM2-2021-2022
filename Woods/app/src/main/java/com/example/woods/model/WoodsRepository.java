package com.example.woods.model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.woods.R;
import com.example.woods.model.local.AppDatabase;
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
    private User finalUser;

    public WoodsRepository(Context context) {
        this.woodsDao = AppDatabase.getInstance(context).getWoodsDao();
        //this.ordersDao = AppDatabase.getInstance(context).getOrdersDao();
        this.context = context;
    }


    public LiveData<User> getUser(Context context,String email, String password) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        WoodsService service = DataSource.getService();
        service.getUserByEmailAndPassword(email,password).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users.size() > 0) {
                        userMutableLiveData.postValue(response.body().get(0));
                    }else {
                        userMutableLiveData.postValue(null);
                        Toast toast = Toast.makeText(context, R.string.ERROR_PASSWORD,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                userMutableLiveData.postValue(null);
            }
        });

        return userMutableLiveData;
    }

    public void addOrder(Orders orders){
        WoodsService service = DataSource.getService();
        Call<Orders> call = service.addOrder(orders);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                Orders orders1 = response.body();
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
    }

    public User seeIfExist(String name, String email, String password, int phone, String birthday) {
        WoodsService service = DataSource.getService();
        Call<List<User>> call = service.getUserByEmail(email);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body();
                    if (userList.size() > 0) {
                        finalUser = null;
                    }else {
                        User user = User.createUser(name,email,password,phone,birthday);
                        finalUser = user;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        return finalUser;
    }

    public LiveData<List<Woods>> getWoods() {
        this.updateWoods();
        return this.woodsDao.getAllWoods();
    }

    public void updateWoods() {
        WoodsService service = DataSource.getService();
        Call<List<Woods>> call = service.getAllWoods();
        call.enqueue(new Callback<List<Woods>>() {
            @Override
            public void onResponse(Call<List<Woods>> call, Response<List<Woods>> response) {
                if (response.isSuccessful()) {
                    List<Woods> postList = response.body();

                    if (postList.size() > 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (Woods woods :
                                        postList) {
                                    woodsDao.add(woods);
                                }
                            }
                        }).start();
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<List<Woods>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<Woods> getWoodById(int id) {
        return this.woodsDao.getWoodById(id);
    }

    public LiveData<User> createUser(User user) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        WoodsService service = DataSource.getService();
        service.addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userMutableLiveData.postValue(response.body());
                } else {
                    userMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userMutableLiveData.postValue(null);
            }
        });
        return userMutableLiveData;
    }


    public LiveData<List<Orders>> getOrders(User user) {
        MutableLiveData<List<Orders>> userMutableLiveData = new MutableLiveData<>();
        WoodsService service = DataSource.getService();
        service.getOrderById(user.getId()).enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if (response.isSuccessful()) {
                    userMutableLiveData.postValue(response.body());
                } else {
                    userMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                userMutableLiveData.postValue(null);
            }
        });
        return userMutableLiveData;
    }

    public void updateUser(int id, User user) {
        WoodsService service = DataSource.getService();
        Call<User> call = service.updateUser(id,user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                }else {

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
