package com.SleepDebtTracker;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.StringTokenizer;


public class AlarmService extends Service
{
    AlarmBroadCast alarm = new AlarmBroadCast();
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.setAlarm(this);
        Log.e("myTag","OnStartCommand");
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        alarm.setAlarm(this);
        Log.e("myTag","onStart");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
//public class AlarmService extends Service {
//
//    private final static String TAG = "BroadcastService";
//
//    public static final String COUNTDOWN_BR = "com.SleepDebtTracker.AlarmBroadCast";
//    Intent bi = new Intent(COUNTDOWN_BR);
//
//    CountDownTimer cdt = null;
//
//    SessionManager sessionManager;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        Log.i(TAG, "Starting timer...");
//        sessionManager=new SessionManager(this);
//
//        cdt = new CountDownTimer(5000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
//                bi.putExtra("countdown", millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                Log.e("myTag","aralm finish");
//                Log.i(TAG, "Timer finished");
//                sendBroadcast(bi);
//
//                Intent intent = new Intent("com.android.techtrainner");
//                intent.putExtra("yourvalue", "torestore");
//                sendBroadcast(intent);
//
//                Intent i=new Intent(AlarmService.this,AlarmRingingActivity.class);
//                startActivity(i);
//
//            }
//        };
//
//        cdt.start();
//    }
//
//    @Override
//    public void onDestroy() {
//
//        cdt.cancel();
//        Log.i(TAG, "Timer cancelled");
//        super.onDestroy();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public IBinder onBind(Intent arg0) {
//        return null;
//    }
//
//
//    public  int getAlarmTime(){
//
//        double t=((sessionManager.getSleepHours()*60)+sessionManager.getSleepMinutes());
//        Log.e("myTag","t: "+String.valueOf(t));
//
//        double dt=t/60;
//        Log.e("myTag","t: "+String.valueOf(dt));
//
//        String currentString = String.valueOf(dt);
//        Log.e("myTag","current: "+currentString);
//        currentString=String.format("%.2f", Double.parseDouble(currentString));
//
//        StringTokenizer tokens = new StringTokenizer(currentString, ".");
//        String first = tokens.nextToken();// this will contain "Fruit"
//        String second = tokens.nextToken();
//
//
//        int h=Integer.valueOf(first);
//        Double m=(Double.valueOf(second)/100)*60;
//
//
//        return (int)Math.ceil(m);
//    }
//}