package com.example.mukulika.sensorapp;

import androidx.room.Ignore;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName="light")
public class Light {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "x")
    public float x;
    public Light(int id,float x){
        this.id=id;
        this.x=x;
    }
    @Ignore
    public Light(float x){
        this.x=x;
    }
}