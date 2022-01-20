package com.example.woods.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.woods.model.local.AppDatabase;

public class SessionManager {
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";

    private static SharedPreferences sharedpreferences;

    private static SharedPreferences getInstance(Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getApplicationContext().getSharedPreferences("Preferences", 0);
        }
        return sharedpreferences;
    }

    public static Users getActiveSession(Context context) {
        if (getInstance(context).contains(KEY_EMAIL) && getInstance(context).contains(KEY_PASSWORD)) {
            Users user = AppDatabase.getInstance(context).getUsersDao().getUserByEmailAndPassword(KEY_EMAIL,KEY_PASSWORD);
            return user;
        }
        return null;
    }

    public static void saveSession(Context context, String email, String password) {
        getInstance(context).edit().putString(KEY_EMAIL, email).apply();
        getInstance(context).edit().putString(KEY_PASSWORD, password).apply();
    }

    public static void clearSession(Context context) {
        getInstance(context).edit().clear().apply();
    }
}
