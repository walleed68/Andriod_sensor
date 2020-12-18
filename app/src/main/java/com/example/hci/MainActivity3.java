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

import static android.util.Half.EPSILON;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;



public class MainActivity3 extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor sensor;

    private static final double NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private double timestamp;
    private TextView textview12,textview13,textview14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        textview12 = findViewById(R.id.textView12);
        textview13 = findViewById(R.id.textView13);
        textview14 = findViewById(R.id.textView14);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        if( sensorManager.getDefaultSensor(sensor.TYPE_GYROSCOPE) == null){
            textview12.setText("Sensor not available");
            textview13.setText("Sensor not available");
            textview14.setText("Sensor not available");
        }

    }



    @Override
    public void onSensorChanged(SensorEvent event) {




        if (timestamp != 0) {
            final double dT = (event.timestamp - timestamp) * NS2S;

            double axisX = event.values[0];
            double axisY = event.values[1];
            double axisZ = event.values[2];


            double omegaMagnitude = sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }

            double thetaOverTwo = omegaMagnitude * dT / 2.0f;
            double sinThetaOverTwo = sin(thetaOverTwo);
            double cosThetaOverTwo = cos(thetaOverTwo);
            deltaRotationVector[0] = (float) (sinThetaOverTwo * axisX);
            textview12.setText(String.valueOf(deltaRotationVector[0]));
            deltaRotationVector[1] = (float) (sinThetaOverTwo * axisY);
            textview13.setText(String.valueOf(deltaRotationVector[1]));
            deltaRotationVector[2] = (float) (sinThetaOverTwo * axisZ);
            textview14.setText(String.valueOf(deltaRotationVector[2]));
            deltaRotationVector[3] = (float) cosThetaOverTwo;
        }
        timestamp = event.timestamp;
        float [] deltaRotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix , deltaRotationVector);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(sensor.TYPE_GYROSCOPE), sensorManager.SENSOR_DELAY_NORMAL);

    }



}




