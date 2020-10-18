package com.example.travel_manager;

// this object holds info of  tourism place visited
public class profile {
   public String name;
    public String  description;
    public String Bio;
    public String PhotoUrl;

    public profile(String name, String description, String bio, String photoUrl) {
        this.name = name;
        this.description = description;
        Bio = bio;
        PhotoUrl = photoUrl;
    }
    public profile() {
        this.name = "name";
        this.description = "description";
        Bio = "bio";
        PhotoUrl = "photoUrl";
    }

}
