package com.example.woods.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.woods.model.Woods;

import java.util.List;

@Dao
public interface WoodsDao {

    @Query("SELECT * FROM Woods")
    LiveData<List<Woods>> getAllWoods();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Woods post);

    @Query("SELECT * FROM Woods WHERE id =:id")
    LiveData<Woods> getWoodById(int id);
}
