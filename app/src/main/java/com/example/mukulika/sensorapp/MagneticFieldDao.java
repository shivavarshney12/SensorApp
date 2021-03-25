package com.example.mukulika.sensorapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MagneticFieldDao {
    @Query("SELECT * FROM magneticField")
    List<MagneticField> getAll();
    @Insert
    void insertAll(MagneticField mf);
}
