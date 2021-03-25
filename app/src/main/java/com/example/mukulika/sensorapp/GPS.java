package com.example.mukulika.sensorapp;


import androidx.room.Ignore;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName="gps")
public class GPS {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "x")
    public double x;
    @ColumnInfo(name = "y")
    public double y;
    public GPS(int id,double x,double y){
        this.id=id;
        this.x=x;
        this.y=y;
    }
    @Ignore
    public GPS(double x,double y){
        this.x=x;
        this.y=y;
    }

}