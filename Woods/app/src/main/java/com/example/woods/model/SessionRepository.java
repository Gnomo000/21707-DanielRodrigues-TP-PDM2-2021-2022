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

    public void saveSession(Users users) {
        SessionManager.saveSession(this.context,users);
    }

    public void clearSession() {
        SessionManager.clearSession(this.context);
    }
}
