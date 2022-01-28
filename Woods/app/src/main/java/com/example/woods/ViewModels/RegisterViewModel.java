package com.example.woods.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.woods.model.SessionRepository;
import com.example.woods.model.User;
import com.example.woods.model.WoodsRepository;

public class RegisterViewModel extends AndroidViewModel {

    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }


    public User seeIfExist(String name, String email, String password, int phone, String birthday){
        return woodsRepository.seeIfExist(name, email,password,phone,birthday);
    }

    public void saveSession(User user) {
        this.sessionRepository.saveSession(user);
    }

    public LiveData<User> createUser(User user) {
        return this.woodsRepository.createUser(user);
    }
}