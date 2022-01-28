package com.example.woods.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.woods.model.SessionRepository;
import com.example.woods.model.User;
import com.example.woods.model.WoodsRepository;

public class LoginViewModel extends AndroidViewModel {

    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }

    public User getActiveSession() {
        return sessionRepository.getActiveSession();
    }

    public LiveData<User> getUser(Context context, String email, String password) {
        return woodsRepository.getUser(context,email,password);
    }

    public void saveSession(User user) {
        this.sessionRepository.saveSession(user);
    }
}