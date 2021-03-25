package com.example.mukulika.sensorapp;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.room.Database;

@Database(entities= {Accelerometer.class,Temperature.class,GPS.class,Light.class,LinearAcceleration.class,MagneticField.class,Proximity.class},exportSchema=true, version=2)
public abstract class Sensors extends RoomDatabase {
    private static final String DB_NAME ="allSensors";
    private static Sensors instance;

    public static synchronized Sensors getInstance(Context context) {
        if (instance == null) {
            instance= Room.databaseBuilder(context.getApplicationContext(),Sensors.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract AccelerometerDao accelerometerDao();
    public abstract GPSDao gpsDao();
    public abstract LightDao lightDao();
    public abstract LinearAccelerationDao linearAccelerationDao();
    public abstract MagneticFieldDao magneticFieldDao();
    public abstract ProximityDao proximityDao();
    public abstract TemperatureDao temperatureDao();
}
