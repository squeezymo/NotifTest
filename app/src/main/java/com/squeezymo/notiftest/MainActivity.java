package com.squeezymo.notiftest;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;
import java.util.Locale;

import static android.R.attr.data;
import static android.R.id.input;

public class MainActivity extends AppCompatActivity {

    private final static int PROGRESS_MIN = 0;
    private final static int PROGRESS_MAX = 100;
    private final static int UPDATE_FREQUENCY_MS = 25;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void performTest(final View view) {
        final NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this);

        final long startTimestamp = SystemClock.uptimeMillis();

        // post an update every UPDATE_FREQUENCY_MS milliseconds
        for (int _progress = PROGRESS_MIN; _progress <= PROGRESS_MAX; _progress++) {
            final int progress = _progress;

            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    final String contentText;

                    if (progress == PROGRESS_MAX) {
                        contentText = "Done!";
                    }
                    else {
                        contentText = String.format(Locale.US, "%d/%d", progress, PROGRESS_MAX);
                    }

                    notifBuilder
                            .setSmallIcon(android.R.drawable.sym_def_app_icon)
                            .setContentTitle("Test")
                            .setProgress(PROGRESS_MAX, progress, false)
                            .setContentText(contentText)
                            .setAutoCancel(false)
                            .setOngoing(true);

                    Log.d("NotifTest", "Progress: " + progress);
                    notifManager.notify(42, notifBuilder.build());
                }
            }, startTimestamp + progress * UPDATE_FREQUENCY_MS);
        }
    }

}
