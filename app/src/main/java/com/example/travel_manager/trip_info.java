package com.example.travel_manager;

public class trip_info {


    public String placeName;
    public String Cost;
    public String review;
    public String latitude;
    public String vicinity;
    public String longitude;

    public trip_info(String placeName, String cost, String review, String vicinity,String latitude, String longitude) {
        this.placeName = placeName;
        this.Cost = cost;
        this.review = review;
        this.latitude = latitude;
        this.vicinity = vicinity;
        this.longitude = longitude;
    }
    public trip_info(){
        this.placeName = "placeName";
        this.Cost = "cost";
        this.review = "review";
        this.latitude = "latitude";
        this.vicinity = "vicinity";
        this.longitude = "longitude";
    }



}
