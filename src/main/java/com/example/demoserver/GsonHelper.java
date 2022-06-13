package com.example.demoserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
    private static Gson gson;

    public static Gson getGson() {
        if(gson!=null){
            return gson;
        }

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create();


        return gson;
    }
}
