package com.example.woods.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.woods.model.SessionRepository;
import com.example.woods.model.Users;
import com.example.woods.model.Woods;
import com.example.woods.model.WoodsRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }

    public void updateList() {
        this.woodsRepository.updateWoods();
    }

    public LiveData<List<Woods>> getWoods() {
        return this.woodsRepository.getWoods();
    }

    public void clearSession() {
        this.woodsRepository.deleteUsers();
        this.sessionRepository.clearSession();
    }

    public Users getActiveSession() {
        return sessionRepository.getActiveSession();
    }

}