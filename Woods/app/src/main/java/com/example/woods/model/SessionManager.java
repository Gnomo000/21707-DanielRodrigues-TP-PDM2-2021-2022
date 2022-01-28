package com.example.woods.model;

import android.content.Context;
import android.content.SharedPreferences;

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

    public static User getActiveSession(Context context) {
        if (getInstance(context).contains(KEY_EMAIL) && getInstance(context).contains(KEY_PASSWORD)) {
            return new User(getInstance(context).getInt(KEY_ID, 0),
                    getInstance(context).getString(KEY_NAME, ""),
                    getInstance(context).getString(KEY_EMAIL, ""),
                    getInstance(context).getString(KEY_PASSWORD, ""),
                    getInstance(context).getInt(KEY_PHONE, 0),
                    getInstance(context).getString(KEY_BIRTHDAY, ""));
        }
        return null;
    }

    public static void saveSession(Context context, User user) {
        getInstance(context).edit().putInt(KEY_ID, user.getId()).apply();
        getInstance(context).edit().putString(KEY_NAME, user.getName()).apply();
        getInstance(context).edit().putString(KEY_EMAIL, user.getEmail()).apply();
        getInstance(context).edit().putString(KEY_PASSWORD, user.getPassword()).apply();
        getInstance(context).edit().putInt(KEY_PHONE, user.getPhone()).apply();
        getInstance(context).edit().putString(KEY_BIRTHDAY, user.getBirthday()).apply();
    }

    public static void saveSessionStrings(Context context, int id, String stringName, String stringEmail, String stringPassword, int parseInt, String stringBirthday) {
        getInstance(context).edit().putInt(KEY_ID, id).apply();
        getInstance(context).edit().putString(KEY_NAME, stringName).apply();
        getInstance(context).edit().putString(KEY_EMAIL, stringEmail).apply();
        getInstance(context).edit().putString(KEY_PASSWORD, stringPassword).apply();
        getInstance(context).edit().putInt(KEY_PHONE, parseInt).apply();
        getInstance(context).edit().putString(KEY_BIRTHDAY, stringBirthday).apply();
    }

    public static void clearSession(Context context) {
        getInstance(context).edit().clear().apply();
    }
}
