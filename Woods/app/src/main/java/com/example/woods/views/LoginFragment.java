package com.example.woods.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.woods.ViewModels.LoginViewModel;
import com.example.woods.R;
import com.example.woods.model.Users;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Users activeSession = mViewModel.getActiveSession();
        if (activeSession != null) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);
        }

        EditText editText = (EditText) view.findViewById(R.id.editTextEmail);
        EditText editText1 = (EditText) view.findViewById(R.id.editTextPassword);

        Button loginButton = view.findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editText.getText().toString();
                String password = editText1.getText().toString();
                Users users = mViewModel.getUser(email,password);
                if (users != null) {
                    mViewModel.saveSession(email,password);
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);
                }
            }
        });

    }
}