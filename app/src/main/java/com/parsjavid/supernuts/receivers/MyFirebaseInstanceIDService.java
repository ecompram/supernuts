package com.parsjavid.supernuts.receivers;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.parsjavid.supernuts.asynkTasks.RefreshTokenAsynkTask;

//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.sanatyar.emdadkeshavarz.Asynktasks.RefreshTokenAsynkTask;


public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "Refreshed token: " + s);
        RefreshTokenAsynkTask a = new RefreshTokenAsynkTask(MyFirebaseInstanceIDService.this, s);
        a.sendRegistrationToServer();
    }
}