package com.example.hci;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textview5,textview6,textview10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textview5 = findViewById(R.id.textView5);
        textview6 = findViewById(R.id.textView6);
        textview10 = findViewById(R.id.textView10);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if( sensorManager.getDefaultSensor(sensor.TYPE_ACCELEROMETER) == null){
            textview5.setText("Sensor not available");
            textview6.setText("Sensor not available");
            textview10.setText("Sensor not available");
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        final double alpha = 0.8;
        double[] gravity = new double[3];
        double[] linear_acceleration = new double[3];

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linear_acceleration[0] = event.values[0] - gravity[0];
        textview5.setText(String.valueOf(linear_acceleration[0]));
        linear_acceleration[1] = event.values[1] - gravity[1];
        textview6.setText(String.valueOf(linear_acceleration[1]));
        linear_acceleration[2] = event.values[2] - gravity[2];
        textview10.setText(String.valueOf(linear_acceleration[2]));


    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);

    }








}