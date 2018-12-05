package com.example.anwarhanif.masjidaroundyou;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;

public class LocationActivity extends AppCompatActivity {

    Button mLocateBtn;
    TextView mLocation;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.w("LocationActivity", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mLocateBtn = (Button) findViewById(R.id.locate_btn);
        mLocation = (TextView) findViewById(R.id.textview1);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.w("LocationActivity", "locationListerner.onLocationChanged()");
                mLocation.append("\n " + location.getLatitude()+" "+location.getLongitude());
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                intent.putExtra("LATITUDE", location.getLatitude());
                intent.putExtra("LONGITUDE", location.getLongitude());
                startActivity(intent);
                finish();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.w("LocationActivity", "locationListener.onProviderDisabled()");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }else{
            Log.w("LocationActivity", "if permission are correct");
            configureButton();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.w("LocationActivity", "onRequestPermissionsResult()");
        switch (requestCode){
            case 10:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    configureButton();
                }
                return;
        }
    }

    private void configureButton(){
        Log.w("LocationActivity", "configureButton()");
        mLocateBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Log.w("LocationActivity", "locateButton()");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
                Log.w("LocationActivity", "locateButton ENDS()");
            }
        });
    }
}
