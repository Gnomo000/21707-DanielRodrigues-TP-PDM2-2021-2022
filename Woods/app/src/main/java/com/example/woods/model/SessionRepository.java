package com.example.woods.model;

import android.content.Context;

public class SessionRepository {
    private Context context;

    public SessionRepository(Context context) {
        this.context = context;
    }

    public User getActiveSession() {
        return SessionManager.getActiveSession(this.context);
    }

    public void saveSession(User user) {
        SessionManager.saveSession(this.context, user);
    }

    public void saveSessionStrings(int id, String stringName, String stringEmail, String stringPassword, int parseInt, String stringBirthday){
        SessionManager.saveSessionStrings(this.context,id,stringName,stringEmail,stringPassword,parseInt,stringBirthday);
    }

    public void clearSession() {
        SessionManager.clearSession(this.context);
    }
}
