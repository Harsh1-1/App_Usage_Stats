package com.example.harshku.first_app;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

public class MJobExecuter extends AsyncTask <Void, String, String> {

    Context ctx;

    public MJobExecuter(Context context){this.ctx = context;}

    @Override
    protected String doInBackground(Void... voids) {

//        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.getDeviceId();

        String android_id = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);


        final UsageStatsManager usageStatsManager = (UsageStatsManager) ctx.getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(Calendar.DAY_OF_MONTH, day);
        beginCal.set(Calendar.MONTH, month-1);
        beginCal.set(Calendar.YEAR, year);

        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.DAY_OF_MONTH, day);
        endCal.set(Calendar.MONTH, month);
        endCal.set(Calendar.YEAR, year);

//          data to be stored in mongodb
        String mdata = "";

        final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, beginCal.getTimeInMillis(), endCal.getTimeInMillis());
//        System.out.println("results for " + beginCal.getTime().toGMTString() + " - " + endCal.getTime().toGMTString());
        Log.d("size of list", Integer.toString(queryUsageStats.size()));
        Log.d("Logging stats", "results for " + beginCal.getTime().toString() + " - " + endCal.getTime().toString());
        for (UsageStats app : queryUsageStats) {
//            System.out.println( app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000) );

            mdata += app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000) + "\n";
            Log.d("Logging stats", app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000));

        }


        String mongostoreurl = "http://159.65.156.101/storeinmongo.php";

        String mongo_data = mdata;
        String device = android_id;
//        String mongo_data = strings[0];
//        String questionID = strings[1];

        try {
            URL url = new URL(mongostoreurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data =  URLEncoder.encode("mongo_data","UTF-8") + "=" + URLEncoder.encode(mongo_data,"UTF-8") + "&"
                    + URLEncoder.encode("device","UTF-8") + "=" + URLEncoder.encode(device,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response = response + line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return response;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }

}
