package com.example.colingleason.lockedon2;

import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.example.colingleason.lockedon2.BlacklistActivity.blacklist;

public class JobSchedulerService extends JobService {
    private static final String TAG = "JobSchedulerService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob:");

        Toast.makeText(getApplicationContext(), "Job started", Toast.LENGTH_SHORT).show();

        Context context = getApplicationContext();

        @SuppressWarnings("WrongConstant")
        UsageStatsManager usm = (UsageStatsManager) getSystemService("usagestats");
        long time = System.currentTimeMillis();
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                time - 1000 * 1000, time);
        String currentApp = null;
        if (appList != null && appList.size() > 0) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getLastTimeUsed(),
                        usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                currentApp = mySortedMap.get(
                        mySortedMap.lastKey()).getPackageName();
            }
        }
        Toast.makeText(getApplicationContext(),currentApp, Toast.LENGTH_SHORT).show();
        //check if the App is blacklisted
        if (currentApp != null) {
            for (String string : blacklist)
            {
                //7Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                if (string.equals(currentApp)) {

                    Toast.makeText(getApplicationContext(),"working", Toast.LENGTH_SHORT).show();
                    //bring lockedOn to the foreground
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // You need this if starting the activity from a service
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    context.startActivity(intent);

                    //kill the blacklisted task which is now in the background
                    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    activityManager.killBackgroundProcesses(currentApp);
                }
            }
        }
        return false;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob:");
        Toast.makeText(getApplicationContext(),"Job finished", Toast.LENGTH_SHORT).show();
        return false;
    }

}