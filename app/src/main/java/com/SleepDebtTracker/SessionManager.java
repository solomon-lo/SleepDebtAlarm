package com.SleepDebtTracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_ALRAM = "IS_ALRAM";
    private static final String IS_LAST_NIGHT = "IS_LAST_NIGHT";
    // email (make variable public to access from outside)
    public static final String KEY_SLEEP_HOURS = "KEY_SLEEP_HOURS";
    public static final String KEY_SLEEP_MMINUTES = "KEY_SLEEP_MMINUTES";
    public static final String KEY_LAST_NIGHT_HOURS = "KEY_LAST_NIGHT";
    public static final String KEY_LAST_NIGHT_MINUTES = "KEY_LAST_NIGHT";
    public static final String KEY_SLEEP_DENT_HOURS = "KEY_SLEEP_DENT_HOURS";
    public static final String KEY_SLEEP_DENT_MINUTES = "KEY_SLEEP_DENT_MINUTES";
    public static final String KEY_SLEEP_DENT_DAY = "KEY_SLEEP_DENT_DAY";
    // Name (make variable public to access from outside)
    public static final String KEY_NAME = "name";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */


    public void createLoginSession(int sleeep_hours,int sleep_minutes){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_SLEEP_HOURS, sleeep_hours);
        editor.putInt(KEY_SLEEP_MMINUTES, sleep_minutes);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
      /*      // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Screensliders.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
            */

            return true;
        }
        return false;
    }


    /**
     * Clear session details
     * */
    public boolean logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SplashActivity.class);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        _context.startActivity(i);

        return true;
    }


    /**
     * Quick check for login
     * **/
    // Get Login State
    private boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isLastNight(){
        return pref.getBoolean(IS_LAST_NIGHT, false);
    }

    public void setLastNightSleep(){
        editor.putBoolean(IS_LAST_NIGHT, true);
        editor.commit();
    }
    public void setLastNightSleepFalse(){
        editor.putBoolean(IS_LAST_NIGHT, false);
        editor.commit();
    }

    public void setLastNight(Integer hours ,Integer m) {
        editor.putInt(KEY_LAST_NIGHT_HOURS, hours);
        editor.putInt(KEY_LAST_NIGHT_MINUTES, m);
        editor.commit();
    }

    public void setSleepHours(Integer hours) {
        editor.putInt(KEY_SLEEP_HOURS, hours);
        editor.commit();
    }

    public void setDentDay(Integer days) {
        editor.putInt(KEY_SLEEP_DENT_DAY, days);
        editor.commit();
    }
    public Integer getDentDay() {
        return pref.getInt(KEY_SLEEP_DENT_DAY, 1);
    }



    public Integer getLastNightHours() {
        return pref.getInt(KEY_LAST_NIGHT_HOURS, 0);
    }
    public Integer getLastNightMinutes() {
        return pref.getInt(KEY_LAST_NIGHT_MINUTES, 0);
    }



    public Integer getSleepHours() {
        return pref.getInt(KEY_SLEEP_HOURS, 0);
    }
    public Integer getSleepMinutes() {
        return pref.getInt(KEY_SLEEP_MMINUTES, 0);
    }


    public Integer getSleepDentHours() {
        return pref.getInt(KEY_SLEEP_DENT_HOURS, 0);
    }
    public Integer getSleepDentMinutes() {
        return pref.getInt(KEY_SLEEP_DENT_MINUTES, 0);
    }
    public void setSleepDent(Integer hours,Integer minutes) {
        editor.putInt(KEY_SLEEP_DENT_HOURS, hours);
        editor.putInt(KEY_SLEEP_DENT_MINUTES, minutes);
        editor.commit();
    }

    public boolean isAlramSet() {

        return pref.getBoolean(IS_ALRAM,false);

    }
    public void setAlramIsOn() {
        editor.putBoolean(IS_ALRAM, true);
        editor.commit();
    }public void setAlramIsOff() {
        editor.putBoolean(IS_ALRAM, false);
        editor.commit();
    }


}