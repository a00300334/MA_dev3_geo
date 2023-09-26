package com.example.geo_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_REQUEST_CODE = 5;
    private TextView locationTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        locationTv = findViewById(R.id.location_tv);

        //Implementing that window that ask you to enable gps location

        //Check if we have permission to use GPS location


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //If not request the permission
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
        }else{
            // all okay we have permission, continue with the app

            // we need location Service
            String locationService = Context.LOCATION_SERVICE;

            // we need service provider
            String serviceProvider = LocationManager.GPS_PROVIDER;

            // we need location Manager
            LocationManager locationManager = (LocationManager) getSystemService(locationService);

            // Last know location
            Location location = locationManager.getLastKnownLocation(serviceProvider);

            displayPosition(location);
        }
    }

    private void displayPosition(Location location) {
        if(location != null){
            String message = String.format("Latitude: %f\nLognitude: %f", location.getLatitude(), location.getLongitude());
            locationTv.setText(message);
        }else{
            locationTv.setText("Something is wrong, did you accepted permissions ?");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //user granted the location permission
        }else{
            // user denied the location permission
            Toast.makeText(this, "This app uses GPS it will not work properly without it.", Toast.LENGTH_LONG).show();
            finishAndRemoveTask();
        }
    }


}