package com.example.woods.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.woods.model.Users;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Users WHERE email =:email AND password =:password")
    LiveData<Users> getUserByEmailAndPassword(String email, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Users users);

    @Query("DELETE FROM Users")
    void delete();
}
