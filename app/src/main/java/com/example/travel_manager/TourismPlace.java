package com.example.travel_manager;

public class TourismPlace{
    public String icon;
    public String name;
    public String vicinity;
    public  String photoUrl;
// <<<<<<< tripupdate
    public double latitude;
    public double longitude;

    public TourismPlace(String icon, String name, String vicinity, String photoUrl, double latitude, double longitude) {
// =======

//     public TourismPlace(String icon, String name, String vicinity, String photoUrl) {
// >>>>>>> master
        this.icon = icon;
        this.name = name;
        this.vicinity = vicinity;
        this.photoUrl = photoUrl;
// <<<<<<< tripupdate
        this.latitude = latitude;
        this.longitude = longitude;
// =======
// >>>>>>> master
    }
}
