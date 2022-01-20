package com.example.woods.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.woods.model.local.AppDatabase;

public class SessionManager {
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_PHONE = "PHONE";
    private static final String KEY_BIRTHDAY = "BIRTHDAY";

    private static SharedPreferences sharedpreferences;

    private static SharedPreferences getInstance(Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getApplicationContext().getSharedPreferences("Preferences", 0);
        }
        return sharedpreferences;
    }

    public static Users getActiveSession(Context context) {
        if (getInstance(context).contains(KEY_EMAIL) && getInstance(context).contains(KEY_PASSWORD)) {
            return new Users(getInstance(context).getInt(KEY_ID, 0),
                    getInstance(context).getString(KEY_NAME, ""),
                    getInstance(context).getString(KEY_EMAIL, ""),
                    getInstance(context).getString(KEY_PASSWORD, ""),
                    getInstance(context).getInt(KEY_PHONE, 0),
                    getInstance(context).getString(KEY_BIRTHDAY, ""));
        }
        return null;
    }

    public static void saveSession(Context context, Users users) {
        getInstance(context).edit().putInt(KEY_ID, users.getId()).apply();
        getInstance(context).edit().putString(KEY_NAME, users.getName()).apply();
        getInstance(context).edit().putString(KEY_EMAIL, users.getEmail()).apply();
        getInstance(context).edit().putString(KEY_PASSWORD, users.getPassword()).apply();
        getInstance(context).edit().putInt(KEY_PHONE, users.getPhone()).apply();
        getInstance(context).edit().putString(KEY_BIRTHDAY, users.getBirthday()).apply();
    }

    public static void clearSession(Context context) {
        getInstance(context).edit().clear().apply();
    }
}
