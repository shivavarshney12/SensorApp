package com.example.mukulika.sensorapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TemperatureDao {
    @Query("SELECT * FROM temperature")
    List<Temperature> getAll();

    @Insert
    void insertAll(Temperature acc);

}
