package com.example.changheonkim.momfirebaseauth;

import java.util.HashMap;
import java.util.Map;

public class ImageDTO {

//    public ImageDTO(){}
    public String imageUrl;
    public String title;
    public String description;
    public String uid;
    public String userId;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

}
