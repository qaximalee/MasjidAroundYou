package com.example.anwarhanif.masjidaroundyou;

public class PrayerTiming {

    private String fajr;
    private String zohr;
    private String asr;
    private String maghrib;
    private String isha;
    private String jummah;

    public PrayerTiming(String fajr, String zohr, String asr, String maghrib, String isha) {
        this.fajr = fajr;
        this.zohr = zohr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
        this.jummah = jummah;
    }

    public String getFajr() {
        return fajr;
    }

    public String getZohr() {
        return zohr;
    }

    public String getAsr() {
        return asr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public String getIsha() {
        return isha;
    }

    public String getJummah() {
        return jummah;
    }
}
