package com.android.proteam;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "proteamPref12332141";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_LAST_NAME = "key_retailer_name";
    private static final String KEY_QUARANTINE_STATUS = "key_phone_number";
    private static final String KEY_STATUS = "key_password";
    private static final String KEY_NOTIFY_ID = "key_agent_id";
    private static final String KEY_ID = "key_id";
    private static final String KEY_IS_LOGGEDIN = "key_is_login";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveDetails(String name,String lastname,String noti_key,String qstatus,String status,int id,boolean isLogin) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_LAST_NAME, lastname);
        editor.putString(KEY_QUARANTINE_STATUS ,qstatus);
        editor.putString(KEY_STATUS ,status);
        editor.putString(KEY_NOTIFY_ID ,noti_key);
        editor.putInt(KEY_ID ,id);
        editor.putBoolean(KEY_IS_LOGGEDIN ,isLogin);
        editor.apply();
    }

    public String getKeyNotifyId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NOTIFY_ID, "");
    }
    public int getId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, 0);
    }


    public String getKeyName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getKeyLastName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LAST_NAME, "");
    }

    public String getKeyQuarantineStatus()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_QUARANTINE_STATUS, "");
    }

    public String getKeyStatus()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_STATUS, "");
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
