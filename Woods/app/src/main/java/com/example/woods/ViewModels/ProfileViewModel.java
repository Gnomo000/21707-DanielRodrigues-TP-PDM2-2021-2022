package com.example.woods.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.woods.model.SessionRepository;
import com.example.woods.model.User;
import com.example.woods.model.WoodsRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }

    public User getActiveSession() {
        return sessionRepository.getActiveSession();
    }


    public void updateUser(int id, User user) {
        this.woodsRepository.updateUser(id,user);
    }

    public void saveSessionStrings(int id, String stringName, String stringEmail, String stringPassword, int parseInt, String stringBirthday) {
        this.sessionRepository.saveSessionStrings(id,stringName,stringEmail,stringPassword,parseInt,stringBirthday);
    }
}