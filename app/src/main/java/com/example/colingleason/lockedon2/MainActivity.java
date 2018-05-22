package com.example.colingleason.lockedon2;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static com.example.colingleason.lockedon2.R.layout.*;

public class MainActivity extends AppCompatActivity {

    static LatLng unlockLocation = null;
    public static boolean unlockLocationReached = true;
    private static final String TAG = "MainActivity";
    JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //automatically exit application if location permission is denied
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }

    public void authenticateBtnClicked(View view){
        if(unlockLocationReached){
            //get the jobscheduler and cancel all jobs called by LockedOn
            mJobScheduler = (JobScheduler)
                    getSystemService(Context.JOB_SCHEDULER_SERVICE);
            mJobScheduler.cancelAll();

            Toast.makeText(this, "You have reached your unlock location. App restriction is now disabled",
            Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Unlock location not yet reached, restriction continuing", Toast.LENGTH_SHORT );
        }
    }

    public void overrideBtnClicked(View view){
        mJobScheduler = (JobScheduler)
                getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.cancelAll();

        Toast.makeText(this, "App restriction has been manually overrode",
                Toast.LENGTH_SHORT).show();
    }

    public void confirmBtnClicked(View view){
        Intent intent = new Intent(this, LocationMonitoringActivity.class);
        startActivity(intent);
    }

    public void btnClicked1(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void savedBtnClicked(View view){
        Intent intent = new Intent(this, SavedLocationActivity.class);
        startActivity(intent);
    }

    public void blacklistBtnClicked(View view){
        Intent intent = new Intent(this, BlacklistActivity.class);
        startActivity(intent);
    }

    public void beginAppRestrictionBtnClicked(View view){
        Log.i(TAG, "onCreate: ");

        mJobScheduler = (JobScheduler)
                getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(),
                        JobSchedulerService.class.getName()));
        builder.setPeriodic(10000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        mJobScheduler.schedule(builder.build());

        unlockLocationReached = false;

        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(i);
       // if (mJobScheduler.schedule(builder.build()) <= 0) {
          //  Log.e(TAG, "onCreate: Some error while scheduling the job");
        //}
        //Toast.makeText(this, "hellooooo", Toast.LENGTH_LONG).show();
    }
}

