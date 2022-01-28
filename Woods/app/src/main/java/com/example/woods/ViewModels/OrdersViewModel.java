package com.example.woods.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.woods.model.Orders;
import com.example.woods.model.SessionRepository;
import com.example.woods.model.User;
import com.example.woods.model.Woods;
import com.example.woods.model.WoodsRepository;

import java.util.List;

public class OrdersViewModel extends AndroidViewModel {
    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public OrdersViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }

    public User getActiveSession() {
        return sessionRepository.getActiveSession();
    }

    public LiveData<List<Orders>> getOrders(User user) {
        return this.woodsRepository.getOrders(user);
    }
}