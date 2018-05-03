package com.example.harshku.first_app;

import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyAppStatService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.harshku.first_app.action.FOO";
    private static final String ACTION_BAZ = "com.example.harshku.first_app.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.harshku.first_app.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.harshku.first_app.extra.PARAM2";

    public MyAppStatService() {
        super("MyAppStatService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyAppStatService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyAppStatService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }


//            final UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
//            Calendar beginCal = Calendar.getInstance();
//            beginCal.set(Calendar.DAY_OF_MONTH, 8);
//            beginCal.set(Calendar.MONTH, 4);
//            beginCal.set(Calendar.YEAR, 2018);
//
//            Calendar endCal = Calendar.getInstance();
//            endCal.set(Calendar.DAY_OF_MONTH, 9);
//            endCal.set(Calendar.MONTH, 4);
//            endCal.set(Calendar.YEAR, 2018);
//
//            final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), endCal.getTimeInMillis());
////        System.out.println("results for " + beginCal.getTime().toGMTString() + " - " + endCal.getTime().toGMTString());
//            Log.d("size of list",Integer.toString(queryUsageStats.size()) );
//            Log.d("Logging stats", "results for " + beginCal.getTime().toString() + " - " + endCal.getTime().toString());
//            for (UsageStats app : queryUsageStats) {
////            System.out.println( app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000) );
//                Log.d("Logging stats" ,app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000) );
//
//            }

        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
