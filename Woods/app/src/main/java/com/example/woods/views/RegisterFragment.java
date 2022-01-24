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
import android.widget.Toast;

import com.example.woods.R;
import com.example.woods.ViewModels.RegisterViewModel;
import com.example.woods.model.Users;

import java.util.List;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        EditText name = view.findViewById(R.id.editTextRegisterName);
        EditText email = view.findViewById(R.id.editTextRegisterEmail);
        EditText password = view.findViewById(R.id.editTextRegisterPassword);
        EditText phone = view.findViewById(R.id.editTextRegisterPhone);
        EditText birthday = view.findViewById(R.id.editTextRegisterBirthday);
        Button newUser = view.findViewById(R.id.buttonNewRegister);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringName = name.getText().toString();
                String stringEmail = email.getText().toString();
                String stringPassword = password.getText().toString();
                String intPhone = phone.getText().toString();
                String stringBirthday = birthday.getText().toString();

                if (!stringName.isEmpty() && !stringEmail.isEmpty() && !stringPassword.isEmpty() && !intPhone.isEmpty() && !stringBirthday.isEmpty()) {
                    Users usersSeeIfExist = mViewModel.seeIfExist(stringName,stringEmail,stringPassword,Integer.parseInt(intPhone),stringBirthday);
                    if (usersSeeIfExist == null) {
                        Users users = Users.createUser(stringName,stringEmail,stringPassword,Integer.parseInt(intPhone),stringBirthday);
                        mViewModel.addUserAtDAO(users);
                        mViewModel.saveSession(users);
                        NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToMainFragment();
                        NavHostFragment.findNavController(RegisterFragment.this).navigate(action);
                    }else {
                        Toast toast = Toast.makeText(RegisterFragment.this.getContext(), R.string.ERROR_USER_EXIST,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else {
                    Toast toast = Toast.makeText(RegisterFragment.this.getContext(), R.string.ERROR_ADDUSER,Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}