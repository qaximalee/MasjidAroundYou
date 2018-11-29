package com.example.anwarhanif.masjidaroundyou;

public class MasjidPOJO {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private PrayerTiming prayerTimimg;

    public MasjidPOJO(int id, String name, double latitude, double longitude, PrayerTiming prayerTimimg) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.prayerTimimg = prayerTimimg;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public PrayerTiming getPrayerTimimg() {
        return prayerTimimg;
    }
}
