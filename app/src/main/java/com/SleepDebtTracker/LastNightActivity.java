package com.SleepDebtTracker;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.StringTokenizer;

public class LastNightActivity extends AppCompatActivity {

    NumberPicker hoursPicker,minutesPicker;
    Button btn_last_night;
    SessionManager sessionManager;
    String nums[]= {
            "1","1.5",
            "2","2.5",
            "3","3.5",
            "4","4.5",
            "5","5.5",
            "6","6.5",
            "7","7.5",
            "8","8.5",
            "9","9.5",
            "10","10.5",
            "11","11.5",
            "12","12.5",
            "13","13.5",
            "14","14.5",
            "15","15.5",
            "16","16.5",
            "17","17.5",
            "18","18.5",
            "19","19.5",
            "20",
            "21",
            "22",
            "23",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_night);

        hoursPicker=findViewById(R.id.numberPicker1);
        minutesPicker=findViewById(R.id.numberPicker2);
        btn_last_night=findViewById(R.id.btn_last_night);

        sessionManager=new SessionManager(LastNightActivity.this);

//        hoursPicker.setMinValue(1);
//        hoursPicker.setMaxValue(24);
//        hoursPicker.setValue(8);

        hoursPicker.setMaxValue(23);
        hoursPicker.setMinValue(1);
        hoursPicker.setWrapSelectorWheel(false);
        hoursPicker.setValue(8);
        hoursPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(hoursPicker, Color.WHITE);

        minutesPicker.setMaxValue(59);
        minutesPicker.setMinValue(0);
        minutesPicker.setWrapSelectorWheel(false);
        minutesPicker.setValue(0);
        minutesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(minutesPicker, Color.WHITE);

        btn_last_night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionManager.setLastNight(hoursPicker.getValue(),minutesPicker.getValue());

                //Integer h,m;

                Log.e("myTag","sessionManager.getSleepDentHours() "+String.valueOf(sessionManager.getSleepDentHours()));
                Log.e("myTag","sessionManager.getSleepHours() "+sessionManager.getSleepHours());
                Log.e("myTag","sessionManager.getLastNightHours() "+sessionManager.getLastNightHours());
                Log.e("myTag","hoursPicker.getValue() "+hoursPicker.getValue());

                Log.e("myTag","sessionManager.getSleepDentMinutes() "+String.valueOf(sessionManager.getSleepDentMinutes()));
                Log.e("myTag","sessionManager.getSleepMinutes() "+sessionManager.getSleepMinutes());
                Log.e("myTag","sessionManager.getLastNightMinutes() "+sessionManager.getLastNightMinutes());
                Log.e("myTag","minutesPicker.getValue()"+minutesPicker.getValue());



                double t=((sessionManager.getSleepHours()*60)+sessionManager.getSleepMinutes())-((hoursPicker.getValue()*60)+minutesPicker.getValue())+(((sessionManager.getSleepDentHours()*60) + sessionManager.getSleepDentMinutes())
                        /sessionManager.getDentDay());
                Log.e("myTag","t: "+String.valueOf(t));

                double dt=t/60;
                Log.e("myTag","t: "+String.valueOf(dt));

                String currentString = String.valueOf(dt);
                Log.e("myTag","current: "+currentString);
                currentString=String.format("%.2f", Double.parseDouble(currentString));

                StringTokenizer tokens = new StringTokenizer(currentString, ".");
                String first = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();


                int h=Integer.valueOf(first); // this will contain "Fruit"
                Double m=(Double.valueOf(second)/100)*60; // this will contain " they taste good"

                Log.e("myTag","End if h: "+h+ " m: "+String.valueOf((int)Math.ceil(m)));
                sessionManager.setSleepDent(h,(int)Math.ceil(m));


                sessionManager.setLastNightSleep();

                Intent intent = new Intent(LastNightActivity.this, ReganDayActivity.class);


//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
