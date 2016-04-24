package com.example.android.exampal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by Vedant on 4/24/2016.
 */
public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    final int PRIVATE_MODE=0;
    private static final String PREF_NAME = "LOG_IN_PREFERENCE";
    private static final String KEY_NAME = "name";
    private static final String KEY_RNO = "RoomNo";
    private static final String KEY_DEPT = "Dept";
    private static final String KEY_MAIL = "email";
    private static final String KEY_CHECK = "IS_Loggedin";

    public SessionManagement(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void loginSession(String name,String roomNo,String dept, String email){
        editor.putBoolean(KEY_CHECK,true);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_RNO,roomNo);
        editor.putString(KEY_DEPT,dept);
        editor.putString(KEY_MAIL,email);
        editor.commit();

    }

    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> userDetails = new HashMap<>();
        userDetails.put(KEY_NAME,sharedPreferences.getString(KEY_NAME,null));
        userDetails.put(KEY_RNO,sharedPreferences.getString(KEY_RNO,null));
        userDetails.put(KEY_DEPT,sharedPreferences.getString(KEY_DEPT,null));
        userDetails.put(KEY_MAIL,sharedPreferences.getString(KEY_MAIL,null));

        return userDetails;

    }

    public void LogOut(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);

    }
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_CHECK,false);
    }






}
