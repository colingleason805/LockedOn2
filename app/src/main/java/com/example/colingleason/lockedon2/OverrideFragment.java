package com.example.colingleason.lockedon2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.job.JobScheduler;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OverrideFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.override_dialogue)
                .setPositiveButton(R.string.override, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         //TODO: add code to stop background application monitoring
                        //get the jobscheduler and cancel all jobs called by LockedOn
                        JobScheduler mJobScheduler = (JobScheduler)
                                getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                        mJobScheduler.cancelAll();
                    }
                })

                //set the Cancel Button to return the user to the UI home screen
                .setNegativeButton(R.string.dont_override, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //launch mainactivity
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
