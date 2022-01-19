package com.example.woods.model;

import android.content.Context;

import com.example.woods.model.local.AppDatabase;
import com.example.woods.model.local.OrdersDao;
import com.example.woods.model.local.UsersDao;
import com.example.woods.model.local.WoodsDao;

public class WoodsRepository {

    private final Context context;
    private WoodsDao woodsDao;
    private OrdersDao ordersDao;
    private UsersDao usersDao;

    public WoodsRepository(Context context) {
        this.woodsDao = AppDatabase.getInstance(context).getWoodsDao();
        this.ordersDao = AppDatabase.getInstance(context).getOrdersDao();
        this.usersDao = AppDatabase.getInstance(context).getUsersDao();
        this.context = context;
    }
    
}
