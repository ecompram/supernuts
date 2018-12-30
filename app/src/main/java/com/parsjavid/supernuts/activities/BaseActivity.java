package com.parsjavid.supernuts.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.parsjavid.supernuts.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseActivity extends AppCompatActivity {

    public FragmentManager _frgManager;
    public Context mContext;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            /*Manifest.permission.READ_CALL_LOG,*/
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.CAMERA,
            /* Manifest.permission.RECEIVE_SMS,*/
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this._frgManager = getSupportFragmentManager();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent("PERMISSION_RECEIVER");
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("permissions", this.permissions);
        intent.putExtra("grantResults", grantResults);
        sendBroadcast(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void addFragment(Fragment frg, int containerId, boolean addToBackStack) {
        //FragmentTransaction ft = this._frgManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out).add(containerId, frg);

        String fragmentTag = frg.getClass().getSimpleName();
        boolean fragmentPopped = _frgManager
                .popBackStackImmediate(fragmentTag, 0);

        FragmentTransaction ftx = _frgManager.beginTransaction();

        if ((!fragmentPopped && _frgManager.findFragmentByTag(fragmentTag) == null) || fragmentTag.contains("Search"))
            ftx.addToBackStack(frg.getClass().getSimpleName());

        ftx.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out, R.anim.fade_in,
                R.anim.fade_out);
        ftx.replace(containerId, frg, fragmentTag);
        ftx.commit();


       /* if (addToBackStack) {
            ft.addToBackStack(frg.getClass().getName());
        }
        ft.commit();*/
    }

}
