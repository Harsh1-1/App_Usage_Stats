package com.example.harshku.first_app;

import android.app.AppOpsManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    UsageStatsManager mUsageStatsManager;
    static final String Tag = "Function checker";
    final String PREFS_NAME = "MyPrefsFile";
    private static final int JOB_ID = 101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(Tag, "Inside OnCreate");


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        Boolean have_permission = settings.getBoolean("permission_granted", false);
        if(!have_permission){
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            Context context = getApplicationContext();

            boolean granted = false;
            AppOpsManager appOps = (AppOpsManager) context
                    .getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), context.getPackageName());

            if (mode == AppOpsManager.MODE_DEFAULT) {
                granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
            } else {
                granted = (mode == AppOpsManager.MODE_ALLOWED);
            }
            Log.d("Permission Granted",Boolean.toString(granted));

            settings.edit().putBoolean("permission_granted",granted).commit();

            finish();
        }
        else{

            ComponentName componentName = new ComponentName(this,MJobScheduler.class);
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);

            builder.setPeriodic(16*60*1000);
            builder.setRequiredNetworkType(jobInfo.NETWORK_TYPE_ANY);
            builder.setPersisted(true);

            jobInfo = builder.build();
            jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);


//            Calendar now = Calendar.getInstance();
//            int year = now.get(Calendar.YEAR);
//            int month = now.get(Calendar.MONTH);
//            int day = now.get(Calendar.DAY_OF_MONTH);
//
//
//            final UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
//            Calendar beginCal = Calendar.getInstance();
//            beginCal.set(Calendar.DAY_OF_MONTH, day - 1);
//            beginCal.set(Calendar.MONTH, month);
//            beginCal.set(Calendar.YEAR, year);
//
//            Calendar endCal = Calendar.getInstance();
//            endCal.set(Calendar.DAY_OF_MONTH, day);
//            endCal.set(Calendar.MONTH, month);
//            endCal.set(Calendar.YEAR, year);
//
////          data to be stored in mongodb
//            String data = "";
//
//            final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), endCal.getTimeInMillis());
////        System.out.println("results for " + beginCal.getTime().toGMTString() + " - " + endCal.getTime().toGMTString());
//            Log.d("size of list", Integer.toString(queryUsageStats.size()));
//            Log.d("Logging stats", "results for " + beginCal.getTime().toString() + " - " + endCal.getTime().toString());
//            for (UsageStats app : queryUsageStats) {
////            System.out.println( app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000) );
//
//                data += app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000) + "\n";
//                Log.d("Logging stats", app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000));
//
//            }

//            StoreAppUsageTask task = new StoreAppUsageTask(getApplicationContext());
//            task.execute(data);

        }

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }
        else
        {
            Log.d("Comments","This is not my first time baby");
        }


//        Intent intent = new Intent(this, MyAppStatService.class);
//        startService(intent);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(Tag, "Inside OnStart");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(Tag,"Inside onPause");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(Tag,"Inside OnREsume");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(Tag, "Inside OnSTop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(Tag, "Inside OnDestroy");
    }


    public void schedulejob(View view) {

        jobScheduler.schedule(jobInfo);
        Toast.makeText(this, "Job scheduled..", Toast.LENGTH_SHORT).show();

    }

    public void clearjob(View view) {

        jobScheduler.cancel(JOB_ID);
        Toast.makeText(this, "Job Cancelled..", Toast.LENGTH_SHORT).show();

    }
}
