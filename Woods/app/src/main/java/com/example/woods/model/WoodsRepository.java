package com.example.woods.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.woods.R;
import com.example.woods.ViewModels.RegisterViewModel;
import com.example.woods.model.local.AppDatabase;
import com.example.woods.model.local.OrdersDao;
import com.example.woods.model.local.UserDao;
import com.example.woods.model.local.WoodsDao;
import com.example.woods.model.remote.DataSource;
import com.example.woods.model.remote.WoodsService;
import com.example.woods.views.LoginFragment;
import com.example.woods.views.LoginFragmentDirections;
import com.example.woods.views.RegisterFragment;
import com.example.woods.views.RegisterFragmentDirections;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoodsRepository {

    private final Context context;
    private WoodsDao woodsDao;
    private OrdersDao ordersDao;
    private UserDao usersDao;


    public WoodsRepository(Context context) {
        this.woodsDao = AppDatabase.getInstance(context).getWoodsDao();
        this.ordersDao = AppDatabase.getInstance(context).getOrdersDao();
        this.usersDao = AppDatabase.getInstance(context).getUsersDao();
        this.context = context;
    }

    public LiveData<Users> getUser(Context context,String email, String password){
        this.updateUsers(context,email,password);
        return this.usersDao.getUserByEmailAndPassword(email,password);
    }

    public void deleteUsers(){
        usersDao.delete();
    }

    public void updateUsers(Context context,String email, String password) {
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
                    }else {
                        Toast toast = Toast.makeText(context, R.string.ERROR_PASSWORD,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void seeIfExist(RegisterFragment registerFragment, RegisterViewModel mViewModel, Context context, String name, String email, String password, int phone, String birthday) {
        WoodsService service = DataSource.getService();
        Call<List<Users>> call = service.getUserByEmail(email);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    List<Users> usersList = response.body();
                    if (usersList.size() > 0) {
                        Toast toast = Toast.makeText(context, R.string.ERROR_PASSWORD,Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        Users users = Users.createUser(name,email,password,phone,birthday);
                        Call<Users> usersCall = service.addUser(users);
                        usersCall.enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {
                                if (response.isSuccessful()) {
                                    Users users = response.body();
                                    usersDao.add(users);
                                    mViewModel.saveSession(users);
                                    NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToMainFragment();
                                    NavHostFragment.findNavController(registerFragment).navigate(action);
                                }
                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });

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
                                // Update list on Room Table
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
}
