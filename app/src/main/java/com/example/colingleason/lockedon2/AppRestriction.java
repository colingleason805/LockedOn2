package com.example.colingleason.lockedon2;

import android.Manifest;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.content.Context;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Colin Gleason on 11/1/2017.
 */

public class AppRestriction {

    Context context;

    AppRestriction(Context context){
        this.context = context;
    }


    //check the app that is in the foreground and return it's package name
    public String checkForegroundApp(){

        AppChecker appChecker = new AppChecker();
        String packageName = appChecker.getForegroundApp(context);
        return packageName;
    }

    public void closeRestrictedApp(String packageName){

        //bring lockedOn to the foreground
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // You need this if starting the activity from a service
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(intent);

        //kill the blacklisted task which is now in the background
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(packageName);

    }


}
