package com.example.travel_manager;

public class TourismPlace{
    public String icon;
    public String name;
    public String vicinity;
    public  String photoUrl;
    public double latitude;
    public double longitude;

    public TourismPlace(String icon, String name, String vicinity, String photoUrl, double latitude, double longitude) {
        this.icon = icon;
        this.name = name;
        this.vicinity = vicinity;
        this.photoUrl = photoUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
