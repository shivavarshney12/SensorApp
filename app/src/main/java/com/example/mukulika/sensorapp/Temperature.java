package com.example.mukulika.sensorapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="temperature")
public class Temperature {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "x")
    public float x;
    public Temperature(int id,float x){
        this.id=id;
        this.x=x;
    }
    @Ignore
    public Temperature(float x){
        this.x=x;
    }
}