package com.example.mukulika.sensorapp;

import androidx.room.Ignore;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName="magneticField")
public class MagneticField {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "x")
    public float x;
    @ColumnInfo(name = "y")
    public float y;
    @ColumnInfo(name = "z")
    public float z;
    public MagneticField(int id,float x,float y,float z){
        this.id=id;
        this.x=x;
        this.y=y;
        this.z=z;
    }
    @Ignore
    public MagneticField(float x,float y,float z){

        this.x=x;
        this.y=y;
        this.z=z;
    }
}