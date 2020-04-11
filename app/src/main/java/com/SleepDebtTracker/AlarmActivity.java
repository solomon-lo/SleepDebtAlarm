package com.SleepDebtTracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.StringTokenizer;


public class AlarmActivity extends AppCompatActivity {

    TextView tv_sleep_dent;
    TextView tv_regan_day;
    TextView tv_sleep_required;

    Button btn_start_alarm;
    Dialog dialog;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        tv_sleep_dent=findViewById(R.id.tv_sleep_dent);
        tv_regan_day=findViewById(R.id.tv_regan_sleep);
        tv_sleep_required=findViewById(R.id.tv_sleep_tonight);
        btn_start_alarm=findViewById(R.id.btn_start_alarm);

        sessionManager=new SessionManager(AlarmActivity.this);



        updateUI();
        initDialog();

        if (sessionManager.isAlramSet()){
            Toast.makeText(AlarmActivity.this,"Alarm is Already On!",Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btn_start_alarm.setBackground(getResources().getDrawable(R.drawable.button_background_red));
                btn_start_alarm.setText("Alarm is On");
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btn_start_alarm.setBackground(getResources().getDrawable(R.drawable.tv_backgound_yellow));
            }
            btn_start_alarm.setText("Set Alarm");
        }

        btn_start_alarm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                if (sessionManager.isAlramSet()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn_start_alarm.setBackground(getResources().getDrawable(R.drawable.button_background_red));

                        stopAlarm();

                    }
                } else {

                    //Create an offset from the current time in which the alarm will go off.
                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.SECOND, 60);
                    cal.add(Calendar.SECOND, calculateAlarmTime()*60);

                    //Create a new PendingIntent and add it to the AlarmManager
                    Intent intent = new Intent(AlarmActivity.this, AlarmRingingActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(AlarmActivity.this,
                            12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager am =
                            (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            pendingIntent);


                    calculateAlarmTime();

                    sessionManager.setAlramIsOn();
                    btn_start_alarm.setText("Alarm is On");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn_start_alarm.setBackground(getResources().getDrawable(R.drawable.button_background_red));
                    }

                    Toast.makeText(AlarmActivity.this, "Now Alarm is On!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    void getReganDays(){

        dialog.setContentView(R.layout.input_sleep_hours);

        Button btn_save_note=dialog.getWindow().findViewById(R.id.btn_save_note);
        Button btn_cancel_note=dialog.getWindow().findViewById(R.id.btn_cancel_note);
        final EditText et_note=dialog.getWindow().findViewById(R.id.et_note);

        et_note.setText(String.valueOf(sessionManager.getDentDay()));
        btn_save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.valueOf(et_note.getText().toString()) != 0) {
                    sessionManager.setDentDay(Integer.valueOf(et_note.getText().toString()));

                    double t=(((sessionManager.getSleepDentHours()*60) + sessionManager.getSleepDentMinutes())
                            /Integer.valueOf(et_note.getText().toString()))+((sessionManager.getSleepHours()*60)+sessionManager.getSleepMinutes());
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


                    tv_sleep_required.setText("Sleep " +h
                            + " Hours " +String.valueOf((int)Math.ceil(m)) + " Minutes Tonight");
                    tv_regan_day.setText("To make up your sleep debt in "+ et_note.getText() + " days");
                    dialog.dismiss();
                }
                else {

                    Toast.makeText(AlarmActivity.this,"Incorrect Input!",Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_cancel_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    void getSleepHours(){

        dialog.setContentView(R.layout.input_days);

        Button btn_save_note=dialog.getWindow().findViewById(R.id.btn_save_note);
        Button btn_cancel_note=dialog.getWindow().findViewById(R.id.btn_cancel_note);
        final NumberPicker hoursPicker=dialog.getWindow().findViewById(R.id.numberPicker1);
        final NumberPicker minutesPicker=dialog.getWindow().findViewById(R.id.numberPicker2);

        hoursPicker.setMaxValue(23);
        hoursPicker.setMinValue(1);
        hoursPicker.setWrapSelectorWheel(false);
        hoursPicker.setValue(sessionManager.getSleepHours());
        hoursPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(hoursPicker, Color.WHITE);

        minutesPicker.setMaxValue(59);
        minutesPicker.setMinValue(0);
        minutesPicker.setWrapSelectorWheel(false);
        minutesPicker.setValue(sessionManager.getSleepMinutes());
        minutesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(minutesPicker, Color.WHITE);

        btn_save_note.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 sessionManager.createLoginSession(hoursPicker.getValue(),minutesPicker.getValue());


//                double t=(((sessionManager.getSleepDentHours()*60) + sessionManager.getSleepDentMinutes())
//                        /sessionManager.getDentDay())+((hoursPicker.getValue()*60)+minutesPicker.getValue());
//                Log.e("myTag","t: "+String.valueOf(t));
//
//                double dt=t/60;
//                Log.e("myTag","t: "+String.valueOf(dt));
//
//                String currentString = String.valueOf(dt);
//                Log.e("myTag","current: "+currentString);
//                currentString=String.format("%.2f", Double.parseDouble(currentString));
//
//                StringTokenizer tokens = new StringTokenizer(currentString, ".");
//                String first = tokens.nextToken();// this will contain "Fruit"
//                String second = tokens.nextToken();
//
//
//                int h=Integer.valueOf(first); // this will contain "Fruit"
//                Double m=(Double.valueOf(second)/100)*60; // this will contain " they taste good"
//
//                Log.e("myTag","End if h: "+h+ " m: "+String.valueOf((int)Math.ceil(m)));
//                sessionManager.setSleepDent(h,(int)Math.ceil(m));



                                                 updateUI();

                                                 dialog.dismiss();
                                             }


                                         }
        );
        btn_cancel_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
    void getLastNight(){

        dialog.setContentView(R.layout.update_lastnight);

        Button btn_save_note=dialog.getWindow().findViewById(R.id.btn_save_note);
        Button btn_cancel_note=dialog.getWindow().findViewById(R.id.btn_cancel_note);
        final NumberPicker hoursPicker=dialog.getWindow().findViewById(R.id.numberPicker1);
        final NumberPicker minutesPicker=dialog.getWindow().findViewById(R.id.numberPicker2);

        hoursPicker.setMaxValue(23);
        hoursPicker.setMinValue(1);
        hoursPicker.setWrapSelectorWheel(false);
        hoursPicker.setValue(sessionManager.getLastNightHours());
        hoursPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(hoursPicker, Color.WHITE);

        minutesPicker.setMaxValue(59);
        minutesPicker.setMinValue(0);
        minutesPicker.setWrapSelectorWheel(false);
        minutesPicker.setValue(sessionManager.getLastNightMinutes());
        minutesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(minutesPicker, Color.WHITE);

        btn_save_note.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {


                                                 sessionManager.setLastNight(hoursPicker.getValue(),minutesPicker.getValue());
                                                 updateUI();

                                                 dialog.dismiss();
                                             }


                                         }
        );
        btn_cancel_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
    void updateUI(){


        tv_sleep_dent.setText(sessionManager.getSleepDentHours() +" Hours "+ sessionManager.getSleepDentMinutes() + " Minutes ");

        tv_regan_day.setText("To make up your sleep debt in "+sessionManager.getDentDay() + " days");

        double t=(((sessionManager.getSleepDentHours()*60) + sessionManager.getSleepDentMinutes())
                /sessionManager.getDentDay())+((sessionManager.getSleepHours()*60)+sessionManager.getSleepMinutes());
        Log.e("myTag","t: "+String.valueOf(t));

        double dt=t/60;
        Log.e("myTag","t: "+String.valueOf(dt));

        String currentString = String.valueOf(dt);
        Log.e("myTag","current: "+currentString);
        currentString=String.format("%.2f", Double.parseDouble(currentString));

        StringTokenizer tokens = new StringTokenizer(currentString, ".");
        String first = tokens.nextToken();// this will contain "Fruit"
        String second = tokens.nextToken();


        int h=Integer.valueOf(first);
        Double m=(Double.valueOf(second)/100)*60;

        tv_sleep_required.setText("Sleep " +h
                + " Hours " +String.valueOf((int)Math.ceil(m)) + " Minutes Tonight");
        Log.e("myTag",String.valueOf( m));
    }
    void initDialog(){
        dialog = new Dialog(AlarmActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RelativeLayout relativeLayout = findViewById(R.id.relative_layout);
        dialog.getWindow().setLayout((int) (relativeLayout.getWidth() ), (int) (relativeLayout.getHeight() ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_sleep_hours:
                getSleepHours();

                break;
            case R.id.menu_last_night:
                getLastNight();

                break;
            case R.id.menu_regan:
                getReganDays();

                break;
            case R.id.menu_reset:
                sessionManager.logoutUser();

                break;
            case R.id.menu_about:

                Intent intent=new Intent(AlarmActivity.this,AboutActivity.class);
                startActivity(intent);


                break;
        }
        return super.onOptionsItemSelected(item);
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

    public int calculateAlarmTime(){

        double t=(((sessionManager.getSleepDentHours()*60) + sessionManager.getSleepDentMinutes())
                /sessionManager.getDentDay())+((sessionManager.getSleepHours()*60)+sessionManager.getSleepMinutes());
        Log.e("myTag","t: "+String.valueOf(t));

        return (int)Math.ceil(t);
    }



    void stopAlarm(){

        dialog.setContentView(R.layout.stop_alarm_dailog);

        Button btn_save_note=dialog.getWindow().findViewById(R.id.btn_save_note);
        Button btn_cancel_note=dialog.getWindow().findViewById(R.id.btn_cancel_note);

        btn_save_note.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 Intent intent = new Intent(AlarmActivity.this, AlarmRingingActivity.class);
                                                 PendingIntent pendingIntent = PendingIntent.getActivity(AlarmActivity.this,
                                                         12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                                 AlarmManager am =
                                                         (AlarmManager)getSystemService(Activity.ALARM_SERVICE);

                                                 am.cancel(pendingIntent);

                                                 sessionManager.setAlramIsOff();
                                                 sessionManager.setLastNightSleepFalse();
                                                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                     btn_start_alarm.setBackground(getResources().getDrawable(R.drawable.tv_backgound_yellow));
                                                 }
                                                 btn_start_alarm.setText("Set Alarm");
                                                 dialog.dismiss();
                                             }


                                         }
        );
        btn_cancel_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
