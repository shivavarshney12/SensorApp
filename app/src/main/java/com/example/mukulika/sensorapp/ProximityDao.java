package com.example.mukulika.sensorapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProximityDao {
    @Query("SELECT * FROM proximity")
    List<Proximity> getAll();
    @Insert
    void insertAll(Proximity p);
}
