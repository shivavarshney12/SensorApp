package com.example.mukulika.sensorapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface AccelerometerDao {
    @Query("SELECT * FROM accelerometer")
    List<Accelerometer> getAll();

    @Query("SELECT * FROM accelerometer where id=1")
    Accelerometer getOne();

    @Insert
    void insertAll(Accelerometer acc);

}