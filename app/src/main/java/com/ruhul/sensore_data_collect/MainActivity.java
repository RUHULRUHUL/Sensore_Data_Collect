package com.ruhul.sensore_data_collect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    FileWriter writer;
    SensorManager manager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        try {
            writer = new FileWriter(new File(getStorageDir(), "sensors_" + System.currentTimeMillis() + ".csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 0);
        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 0);
        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 0);
        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_LIGHT), 0);
    }
    private String getStorageDir() {
        return this.getExternalFilesDir(null).getAbsolutePath();
        //  return "/storage/emulated/0/Android/data/";
    }
    @Override
    public void onSensorChanged(SensorEvent evt) {
            try {
                switch(evt.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        writer.write(String.format("%d; ACC; %f; %f; %f\n", evt.timestamp, evt.values[0], evt.values[1], evt.values[2]));
                        writer.flush();
                        break;
                    case Sensor.TYPE_GYROSCOPE:
                        writer.write(String.format("%d; GYRO; %f; %f; %f\n", evt.timestamp, evt.values[0], evt.values[1], evt.values[2]));
                        writer.flush();
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        writer.write(String.format("%d; MAG; %f; %f; %f; %f\n", evt.timestamp, evt.values[0], evt.values[1], evt.values[2]));
                        writer.flush();
                        break;
                    case Sensor.TYPE_LIGHT:
                        writer.write(String.format("%d; LIGHT; %f; %f; %f\n", evt.timestamp, evt.values[0], 0.f, 0.f));
                        writer.flush();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    @Override
    public void onResume(){
        super.onResume();
        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 0);
        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 0);
        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 0);
        manager.registerListener(MainActivity.this, manager.getDefaultSensor(Sensor.TYPE_LIGHT), 0);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}