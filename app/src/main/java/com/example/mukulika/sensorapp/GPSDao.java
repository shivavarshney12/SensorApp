package com.example.mukulika.sensorapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GPSDao {
    @Query("SELECT * FROM gps")
    List<GPS> getAll();
    @Insert
    void insertAll(GPS gps);
}
