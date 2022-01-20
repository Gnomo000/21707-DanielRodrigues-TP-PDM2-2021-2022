package com.example.woods.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

public class SessionRepository {
    private Context context;

    public SessionRepository(Context context) {
        this.context = context;
    }

    public Users getActiveSession() {
        return SessionManager.getActiveSession(this.context);
    }

    public void saveSession(String email, String password) {
        SessionManager.saveSession(this.context,email,password);
    }

    public void clearSession() {
        SessionManager.clearSession(this.context);
    }
}
