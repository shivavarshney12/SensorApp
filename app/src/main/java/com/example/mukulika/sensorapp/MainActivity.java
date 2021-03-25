package com.example.mukulika.sensorapp;
//took reference from https://stackoverflow.com/questions/49281831/detect-device-is-moving-or-stationary
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;
import java.util.List;
import android.view.View;
import android.location.LocationManager;
import android.location.Location;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private SensorManager mSensorManager;
    private LocationManager locationManager;
    private Sensor mAcc,gravity;
    private Sensor lAcc,tmp,light,gps,proximity,magneticField;
    private double mAccel;
    private double mAccelCurrent;
    private double mAccelLast;

    TextView textView2,textView4,textView6,textView8,textView10,textView12,textView14,textView17;
    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;

    private final int SAMPLE_SIZE = 50; // change this sample size as you want, higher is more precise but slow measure.
    private final double THRESHOLD = 0.2; // change this threshold as you want, higher is more spike movement

    Sensors sdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        textView2 = (TextView)findViewById(R.id.textView2);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView6 = (TextView)findViewById(R.id.textView6);
        textView8 = (TextView)findViewById(R.id.textView8);
        textView10 = (TextView)findViewById(R.id.textView10);
        textView12 = (TextView)findViewById(R.id.textView12);
        textView14 = (TextView)findViewById(R.id.textView14);
        textView17= (TextView)findViewById(R.id.textView17);
        sdb= Sensors.getInstance(this);


        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        tmp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        light = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //gps = mSensorManager.getDefaultSensor(Sensor.TYPE_GPS);
        proximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        magneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    public final void onLocationChanged (Location event) {
        //textView10 = (TextView)findViewById(R.id.textView10);
        double x=event.getLongitude();
        double y=event.getLatitude();
        final GPS gps=new GPS(x,y);
        /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            sdb.gpsDao().insertAll(gps);
                                                        }
                                                    }

        );*/

        textView10.setText("Longitude: "+x+"\nLatitude: "+y);

    }
    @Override
    public final void onProviderDisabled (String provider){

    }
    @Override
    public final void onProviderEnabled (String provider){

    }
    @Override
    public final void onStatusChanged(String Provider,int status, Bundle extras) {

    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy){
        //sensor accuracy changes
    }
    @Override
    public final void onSensorChanged(SensorEvent event){
        ///float x,y,z;
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            final Accelerometer acc=new Accelerometer(x,y,z);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                sdb.accelerometerDao().insertAll(acc);
                                                            }
                                                        }

            );
            //sdb.accelerometerDao().insertAll(acc);
            //List<Accelerometer> list= sdb.accelerometerDao().getOne();
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(x * x + y * y + z * z);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (hitCount <= SAMPLE_SIZE) {
                hitCount++;
                hitSum += Math.abs(mAccel);
            } else {
                hitResult = hitSum / SAMPLE_SIZE;

                if (hitResult > THRESHOLD) {
                    textView17.setText("Moving");
                } else {
                    textView17.setText("Stationary");
                }

                hitCount = 0;
                hitSum = 0;
                hitResult = 0;
            }
            textView2.setText("x: "+x+"\ny: "+y+"\nz: "+z);
        }
        if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            //textView4 = (TextView)findViewById(R.id.textView4);
            final LinearAcceleration la=new LinearAcceleration(x,y,z);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                sdb.linearAccelerationDao().insertAll(la);
                                                            }
                                                        }

            );

            textView4.setText("x: "+x+"\ny: "+y+"\nz: "+z);
        }
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float x = event.values[0];
            //textView6 = (TextView)findViewById(R.id.textView6);
            final Temperature t=new Temperature(x);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                sdb.temperatureDao().insertAll(t);
                                                            }
                                                        }

            );
            textView6.setText("x: "+x);
        }
        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float x = event.values[0];
            //textView8 = (TextView)findViewById(R.id.textView8);
            final Light l=new Light(x);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                sdb.lightDao().insertAll(l);
                                                            }
                                                        }

            );

            textView8.setText("value: "+x);
        }
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float x = event.values[0];
            //textView12 = (TextView)findViewById(R.id.textView12);
            final Proximity p=new Proximity(x);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                sdb.proximityDao().insertAll(p);
                                                            }
                                                        }

            );

            textView12.setText("x: "+x);
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            //textView14 = (TextView)findViewById(R.id.textView14);
            final MagneticField mf=new MagneticField(x,y,z);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                sdb.magneticFieldDao().insertAll(mf);
                                                            }
                                                        }

            );

            textView14.setText("x: "+x+"\ny: "+y+"\nz: "+z);
        }

    }
    @Override
    protected void onResume(){
        super.onResume();
        if(mAcc != null) {
            mSensorManager.registerListener(this,mAcc,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this, "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }
        if(lAcc != null) {
            mSensorManager.registerListener(this,lAcc,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this, "Error: No Linear Acceleration.", Toast.LENGTH_LONG).show();
        }
        if(tmp != null) {
            mSensorManager.registerListener(this,tmp,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this, "Error: No temperature sensor.", Toast.LENGTH_LONG).show();
        }
        if(light != null) {
            mSensorManager.registerListener(this,light,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this, "Error: No light sensor.", Toast.LENGTH_LONG).show();
        }
        if(proximity != null) {
            mSensorManager.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this, "Error: No proximity sensor.", Toast.LENGTH_LONG).show();
        }
        if(magneticField != null) {
            mSensorManager.registerListener(this,magneticField,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this, "Error: No Magnetic Field sensor.", Toast.LENGTH_LONG).show();
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, this);

        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 11);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
        }
        /*if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
        }*/
        //checkPermission({Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, [10,11]);


    }

    // Function to check and request permission.
    /*public void checkPermission(String[] permission, int[] requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(MainActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }*/
    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
        @Override
        public void onRequestPermissionsResult(int requestCode,
        @NonNull String[] permissions,
        @NonNull int[] grantResults)
        {
            super
                    .onRequestPermissionsResult(requestCode,
                            permissions,
                            grantResults);

            if (requestCode == 11) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,
                            "Coarse Location Permission Granted",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "Coarse Location Permission Denied",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
            else if (requestCode == 12) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,
                            "Fine Location Permission Granted",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "Fine Location Permission Denied",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

    public void button1(View v){
        if(((ToggleButton) v).isChecked()) {
            textView2.setVisibility(View.VISIBLE);
        }
        else {
            textView2.setVisibility(View.INVISIBLE);
        }
    }
    public void button2(View v){
        if(((ToggleButton) v).isChecked()) {
            textView4.setVisibility(View.VISIBLE);
        }
        else {
            textView4.setVisibility(View.INVISIBLE);
        }
    }
    public void button3(View v){
        if(((ToggleButton) v).isChecked()) {
            textView6.setVisibility(View.VISIBLE);
        }
        else {
            textView6.setVisibility(View.INVISIBLE);
        }
    }
    public void button4(View v){
        if(((ToggleButton) v).isChecked()) {
            textView8.setVisibility(View.VISIBLE);
        }
        else {
            textView8.setVisibility(View.INVISIBLE);
        }
    }
    public void button5(View v){
        if(((ToggleButton) v).isChecked()) {
            textView10.setVisibility(View.VISIBLE);
        }
        else {
            textView10.setVisibility(View.INVISIBLE);
        }
    }
    public void button6(View v){
        if(((ToggleButton) v).isChecked()) {
            textView12.setVisibility(View.VISIBLE);
        }
        else {
            textView12.setVisibility(View.INVISIBLE);
        }
    }
    public void button7(View v){
        if(((ToggleButton) v).isChecked()) {
            textView14.setVisibility(View.VISIBLE);
        }
        else {
            textView14.setVisibility(View.INVISIBLE);
        }
    }
    public void button8(View v){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            List<Accelerometer> a1= sdb.accelerometerDao().getAll();
                                                            float x=0,y=0,z=0;
                                                            int i,n;
                                                            if(a1.size()<=18000) {
                                                                i=0;n=a1.size();
                                                            }
                                                            else {
                                                                i=a1.size()-18000;n=18000;
                                                            }
                                                            for( ;i<a1.size();i++) {
                                                                x=x+a1.get(i).x;
                                                                y=y+a1.get(i).y;
                                                                z=z+a1.get(i).z;
                                                            }
                                                            x=x/n;y=y/n;z=z/n;
                                                            final float x1=x,y1=y,z1=z;
                                                            runOnUiThread(new Runnable(){
                                                                @Override
                                                                public void run(){
                                                                    TextView textView15=(TextView) findViewById(R.id.textView15);
                                                                    textView15.setText("x: "+x1+"\ny: "+y1+"\nz: "+z1);
                                                                    textView15.setVisibility(View.VISIBLE);
                                                                }
                                                            });

                                                            //Toast.makeText(MainActivity.this, "data: "+list, Toast.LENGTH_LONG).show();
                                                        }
                                                    }

        );


    }
    public void button9(View v){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            List<MagneticField> a1= sdb.magneticFieldDao().getAll();
                                                            float x=0,y=0,z=0;
                                                            int i,n;
                                                            if(a1.size()<=18000) {
                                                                i=0;n=a1.size();
                                                            }
                                                            else {
                                                                i=a1.size()-18000;n=18000;
                                                            }
                                                            for( ;i<a1.size();i++) {
                                                                x=x+a1.get(i).x;
                                                                y=y+a1.get(i).y;
                                                                z=z+a1.get(i).z;
                                                            }
                                                            x=x/n;y=y/n;z=z/n;
                                                            final float x1=x,y1=y,z1=z;
                                                            runOnUiThread(new Runnable(){
                                                                @Override
                                                                public void run(){
                                                                    TextView textView16=(TextView) findViewById(R.id.textView16);
                                                                    textView16.setText("x: "+x1+"\ny: "+y1+"\nz: "+z1);
                                                                    textView16.setVisibility(View.VISIBLE);
                                                                }
                                                            });


                                                            //Toast.makeText(MainActivity.this, "data: "+list, Toast.LENGTH_LONG).show();
                                                        }
                                                    }

        );


    }
    public void button10(View v){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            List<Temperature> a1= sdb.temperatureDao().getAll();
                                                            float x=0;
                                                            int i,n;
                                                            if(a1.size()<=18000) {
                                                                i=0;n=a1.size();
                                                            }
                                                            else {
                                                                i=a1.size()-18000;n=18000;
                                                            }
                                                            for( ;i<a1.size();i++) {
                                                                x=x+a1.get(i).x;
                                                            }
                                                            x=x/n;
                                                            final float x1=x;
                                                            runOnUiThread(new Runnable(){
                                                                @Override
                                                                public void run(){
                                                                    TextView textView18=(TextView) findViewById(R.id.textView18);
                                                                    textView18.setText("x: "+x1);
                                                                    textView18.setVisibility(View.VISIBLE);
                                                                }
                                                            });

                                                            //Toast.makeText(MainActivity.this, "data: "+list, Toast.LENGTH_LONG).show();
                                                        }
                                                    }

        );
    }
}
