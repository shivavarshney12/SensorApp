package com.example.mukulika.sensorapp;

import androidx.room.Ignore;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName="proximity")
public class Proximity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "x")
    public float x;
    public Proximity(int id,float x){
        this.id=id;
        this.x=x;
    }
    @Ignore
    public Proximity(float x){
        this.x=x;
    }

}