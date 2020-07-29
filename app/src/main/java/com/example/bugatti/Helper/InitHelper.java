package com.example.bugatti.Helper;


import android.app.Application;

public class InitHelper extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ServerHelper.start();;

    }
}

