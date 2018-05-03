package com.example.harshku.first_app;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class MJobScheduler extends JobService {

    private MJobExecuter mJobExecuter;


    @Override
    public boolean onStartJob(final JobParameters params) {
        mJobExecuter = new MJobExecuter(getApplicationContext()){

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                jobFinished(params,false);
            }

        };

        mJobExecuter.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        mJobExecuter.cancel(true);
        return false;
    }

}
