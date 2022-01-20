package com.example.woods.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.woods.model.SessionRepository;
import com.example.woods.model.WoodsRepository;

public class MainViewModel extends AndroidViewModel {

    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }

    public void clearSession() {
        this.sessionRepository.clearSession();
    }

}