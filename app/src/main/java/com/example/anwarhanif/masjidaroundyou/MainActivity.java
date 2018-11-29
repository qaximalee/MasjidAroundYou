package com.example.anwarhanif.masjidaroundyou;

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

    private double mLatitude = 24.814992;
    private double mLongitude = 67.110903;

    RecyclerView recyclerView;
    MasjidAdapter adapter;
    private Button mCalculateDistanceBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculateDistanceBtn = (Button) findViewById(R.id.calculate_btn);
        recyclerView = (RecyclerView) findViewById(R.id.masjid_name_n_distance);


        masajid.add(new MasjidPOJO(1, "Sofia Noorbakhsha", 24.859655, 67.088551, new PrayerTiming("5:36 AM", "12:19 PM", "4:06 PM", "5:42 PM", "7:02 PM")));
        masajid.add(new MasjidPOJO(2, "Shamim Masjid", 24.813565, 67.112406, new PrayerTiming("5:40 AM", "12:16 PM", "4:10 PM", "5:40 PM", "7:10 PM")));
        masajid.add(new MasjidPOJO(3, "Ghosia Masjid", 24.814719, 67.108182, new PrayerTiming("5:33 AM", "12:23 PM", "4:02 PM", "5:45 PM", "7:07 PM")));

        mCalculateDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(MasjidPOJO masjidPOJO : masajid){
                    double mLat = masjidPOJO.getLatitude();
                    double mLong = masjidPOJO.getLongitude();

                    double distance = calculateDistance(mLat, mLong);

                    if(distance < 1){
                        nearestMasajids.add(masjidPOJO);
                        masajidDistances.add(calculateDistance(mLat, mLong));
                        Log.d("If statement", "distance < 1");
                    }else {
                        Toast.makeText(MainActivity.this, "Not Near", Toast.LENGTH_SHORT).show();
                    }
                }

                adapter = new MasjidAdapter(MainActivity.this, nearestMasajids, masajidDistances);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

    }

    public double calculateDistance(double lat, double lng){

        double earthRadius = 3958.75;//Value is in Miles

        double dLat = Math.toRadians(lat-mLatitude);
        double dLng = Math.toRadians(lng-mLongitude);

        double sindLat = Math.sin(dLat/2);
        double sindLng = Math.sin(dLng/2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(mLatitude))*Math.cos(Math.toRadians(lat));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distMILE = earthRadius * c;

        double distInKM = distMILE * 1.60934;

        return distInKM;
    }
}
