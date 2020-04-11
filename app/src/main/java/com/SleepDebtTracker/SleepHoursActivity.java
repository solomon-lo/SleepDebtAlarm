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

public class SleepHoursActivity extends AppCompatActivity {

    NumberPicker hoursPicker,minutesPicker;
    Button ban_sleep_hours;
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
            "20","20.5",
            "21","21.5",
            "22","22.5",
            "23","23.5",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_hours);
        sessionManager=new SessionManager(SleepHoursActivity.this);
        hoursPicker=findViewById(R.id.numberPicker1);
        minutesPicker=findViewById(R.id.numberPicker2);
        ban_sleep_hours =findViewById(R.id.btn_last_night);


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

        ban_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("myTag",String.valueOf(hoursPicker.getValue()));
                sessionManager.createLoginSession(hoursPicker.getValue(),minutesPicker.getValue());

                Intent intent = new Intent(SleepHoursActivity.this, LastNightActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
