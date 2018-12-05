package com.example.anwarhanif.masjidaroundyou;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MasjidPOJO> masajid = new ArrayList<>();
    private ArrayList<MasjidPOJO> nearestMasajids = new ArrayList<>();
    private ArrayList<Double> masajidDistances = new ArrayList<>();

    private double mLatitude;// = 24.814992;
    private double mLongitude; //= 67.110903;

    RecyclerView recyclerView;
    MasjidAdapter adapter;
    private Button mCalculateDistanceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculateDistanceBtn = findViewById(R.id.calculate_btn);
        recyclerView = findViewById(R.id.masjid_name_n_distance);


        masajid.add(new MasjidPOJO(1, "Sofia Noorbakhsha", 24.859655, 67.088551, new PrayerTiming("5:36 AM", "12:19 PM", "4:06 PM", "5:42 PM", "7:02 PM")));
        masajid.add(new MasjidPOJO(2, "Shamim Masjid", 24.813565, 67.112406, new PrayerTiming("5:40 AM", "12:16 PM", "4:10 PM", "5:40 PM", "7:10 PM")));
        masajid.add(new MasjidPOJO(3, "Ghosia Masjid", 24.814719, 67.108182, new PrayerTiming("5:33 AM", "12:23 PM", "4:02 PM", "5:45 PM", "7:07 PM")));
        masajid.add(new MasjidPOJO(3, "Subahan Masjid", 24.814729, 67.108172, new PrayerTiming("5:31 AM", "12:21 PM", "4:01 PM", "5:46 PM", "7:09 PM")));

        getLocations();

        mCalculateDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLocations();

                for (MasjidPOJO masjidPOJO : masajid) {

                    double mLat = masjidPOJO.getLatitude();
                    double mLong = masjidPOJO.getLongitude();

                    double distance = calculateDistance(mLat, mLong);

                    if (distance < 1) {
                        nearestMasajids.add(masjidPOJO);
                        masajidDistances.add(calculateDistance(mLat, mLong));
                        Log.d("If statement", "distance < 1");
                    } else {
                        Toast.makeText(MainActivity.this, "Not Near", Toast.LENGTH_SHORT).show();
                    }
                }

                Toast.makeText(MainActivity.this, "Calculating", Toast.LENGTH_SHORT).show();

                adapter = new MasjidAdapter(MainActivity.this, nearestMasajids, masajidDistances);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                mCalculateDistanceBtn.setEnabled(false);
            }
        });

    }

    public double calculateDistance(double lat, double lng){

        double earthRadius = 3958.75;//Value is in Miles

        double dLat = Math.toRadians(lat-getLatitude());
        double dLng = Math.toRadians(lng-getLongitude());

        double sindLat = Math.sin(dLat/2);
        double sindLng = Math.sin(dLng/2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(getLatitude()))*Math.cos(Math.toRadians(lat));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distMILE = earthRadius * c;

        double distInKM = distMILE * 1.60934;

        return distInKM;
    }

    public void setLatitude(double latitude){
        Log.w("MainActivity", "setLatitude()");
        this.mLatitude = latitude;
    }

    public void setLongitude(double longitude){
        Log.w("MainActivity", "setLongitude()");
        this.mLongitude = longitude;
    }

    public double getLatitude(){
        return this.mLatitude;
    }

    public double getLongitude(){
        return this.mLongitude;
    }

    public void getLocations(){
        Log.w("MainActivity", "getLocations()");

        LocationManager locationManager;
        LocationListener locationListener;

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        Looper looper = null;

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.w("MainActivity", "onLocationChanged()");
                setLatitude(location.getLatitude());
                setLongitude(location.getLongitude());

                Toast.makeText(MainActivity.this, "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.w("MainActivity", "locationListener.onProviderDisabled()");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }
        }else{
            Log.w("MainActivity", "if permission are correct");
            locationManager.requestSingleUpdate(criteria, locationListener, looper);
        }
    }
}
