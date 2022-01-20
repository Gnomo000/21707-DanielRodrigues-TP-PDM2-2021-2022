package com.example.woods.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.woods.model.SessionRepository;
import com.example.woods.model.Users;
import com.example.woods.model.WoodsRepository;

public class LoginViewModel extends AndroidViewModel {

    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }

    public Users getActiveSession() {
        return sessionRepository.getActiveSession();
    }

    public LiveData<Users> getUser(String email, String password) {
        return woodsRepository.getUser(email,password);
    }

    public void saveSession(Users users) {
        this.sessionRepository.saveSession(users);
    }
}