package com.example.woods.views;

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

import com.example.woods.ViewModels.ProfileViewModel;
import com.example.woods.R;
import com.example.woods.model.User;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private String dateFinal = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        EditText name = view.findViewById(R.id.editTextSaveName);
        EditText email = view.findViewById(R.id.editTextSaveEmail);
        EditText password = view.findViewById(R.id.editTextSavePassword);
        EditText phone = view.findViewById(R.id.editTextSavePhone);
        Button birthday = view.findViewById(R.id.buttonSaveBirthday);
        Button newUser = view.findViewById(R.id.buttonSave);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        User user = this.mViewModel.getActiveSession();

        name.setText(user.getName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        phone.setText(String.valueOf(user.getPhone()));
        List<String> dateSplit = Arrays.asList(user.getBirthday().split("-"));
        birthday.setText(dateSplit.get(2)+"/"+dateSplit.get(1)+"/"+dateSplit.get(0));

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
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

                User newUser = User.createUser(stringName,stringEmail,stringPassword,Integer.parseInt(intPhone),stringBirthday);
                mViewModel.updateUser(mViewModel.getActiveSession().getId(),newUser);

                mViewModel.saveSessionStrings(mViewModel.getActiveSession().getId(),stringName,stringEmail,stringPassword,Integer.parseInt(intPhone),stringBirthday);
                NavDirections action = ProfileFragmentDirections.actionProfileFragmentToMainFragment();
                NavHostFragment.findNavController(ProfileFragment.this).navigate(action);
            }
        });

    }
}