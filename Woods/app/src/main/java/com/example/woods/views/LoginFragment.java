package com.example.woods.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.woods.ViewModels.LoginViewModel;
import com.example.woods.R;
import com.example.woods.model.User;

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

        User activeSession = mViewModel.getActiveSession();
        if (activeSession != null) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainFragment();
            NavHostFragment.findNavController(LoginFragment.this).navigate(action);
        }

        EditText email = view.findViewById(R.id.editTextEmail);
        EditText password = view.findViewById(R.id.editTextPassword);

        Button loginButton = view.findViewById(R.id.button);
        Button siginButton = view.findViewById(R.id.buttonRegister);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getUser(LoginFragment.this.getContext(),email.getText().toString(),password.getText().toString()).observe(getViewLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user != null) {
                            mViewModel.saveSession(user);
                            NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainFragment();
                            NavHostFragment.findNavController(LoginFragment.this).navigate(action);
                        }
                    }
                });
            }
        });

        siginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
                NavHostFragment.findNavController(LoginFragment.this).navigate(action);
            }
        });

    }
}