package com.example.woods.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.woods.model.SessionRepository;
import com.example.woods.model.Users;
import com.example.woods.model.WoodsRepository;
import com.example.woods.views.RegisterFragment;

public class RegisterViewModel extends AndroidViewModel {

    private final SessionRepository sessionRepository;
    private WoodsRepository woodsRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.sessionRepository = new SessionRepository(application.getApplicationContext());
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
    }


    public void seeIfExist(RegisterFragment registerFragment, RegisterViewModel mViewModel, Context context, String name, String email, String password, int phone, String birthday){
        woodsRepository.seeIfExist(registerFragment,mViewModel,context,name, email,password,phone,birthday);
    }

    public void saveSession(Users users) {
        this.sessionRepository.saveSession(users);
    }

}