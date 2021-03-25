package com.example.mukulika.sensorapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LinearAccelerationDao {
    @Query("SELECT * FROM linearAcceleration")
    List<LinearAcceleration> getAll();
    @Insert
    void insertAll(LinearAcceleration la);
}
