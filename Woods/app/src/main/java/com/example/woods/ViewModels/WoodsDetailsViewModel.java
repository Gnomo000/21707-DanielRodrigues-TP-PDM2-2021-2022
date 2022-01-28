package com.example.woods.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.woods.model.Orders;
import com.example.woods.model.SessionRepository;
import com.example.woods.model.Woods;
import com.example.woods.model.WoodsRepository;

public class WoodsDetailsViewModel extends AndroidViewModel {
    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public WoodsDetailsViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }

    public LiveData<Woods> getWoodById (int id) {
        return this.woodsRepository.getWoodById(id);
    }

    public void addOrder(Orders orders){
        woodsRepository.addOrder(orders);
    }

}