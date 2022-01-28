package com.example.woods.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.woods.R;
import com.example.woods.ViewModels.RegisterViewModel;
import com.example.woods.model.User;

import java.util.Calendar;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private String dateFinal = null;

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
        Button birthday = view.findViewById(R.id.buttonRegisterBirthday);
        Button newUser = view.findViewById(R.id.buttonNewRegister);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        dateFinal = year+"-"+month+"-"+day;
                        birthday.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringName = name.getText().toString();
                String stringEmail = email.getText().toString();
                String stringPassword = password.getText().toString();
                String intPhone = phone.getText().toString();
                String stringBirthday = dateFinal;

                if (!stringName.isEmpty() && !stringEmail.isEmpty() && !stringPassword.isEmpty() && !intPhone.isEmpty() && !stringBirthday.isEmpty()) {
                    User userSeeIfExist = mViewModel.seeIfExist(stringName,stringEmail,stringPassword,Integer.parseInt(intPhone),stringBirthday);
                    if (userSeeIfExist == null) {
                        User user = User.createUser(stringName,stringEmail,stringPassword,Integer.parseInt(intPhone),stringBirthday);
                        mViewModel.createUser(user).observe(getViewLifecycleOwner(), new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                if (user != null) {
                                    mViewModel.saveSession(user);
                                    NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToMainFragment();
                                    NavHostFragment.findNavController(RegisterFragment.this).navigate(action);
                                } else {
                                    Toast toast = Toast.makeText(RegisterFragment.this.getContext(), R.string.ERROR_USER_EXIST,Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        });
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