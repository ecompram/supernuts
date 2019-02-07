package com.parsjavid.supernuts.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseActivity extends AppCompatActivity {

    public FragmentManager _frgManager;
    public Context mContext;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    int previousSelectedBottomNavigationView ;

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

        //setContentView(getLayoutId());

        setupToolbar();

        setupBottomNavigationView();
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

    public void setupToolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.mainToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (bottomNavigationView != null) {

            Intent intent = getIntent();
            previousSelectedBottomNavigationView = intent.getIntExtra("previousSelectedBottomNavigationView", 0);

            bottomNavigationView.setSelectedItemId(previousSelectedBottomNavigationView);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    if (menuItem.getItemId() == R.id.mnuSearchProduct && previousSelectedBottomNavigationView !=R.id.mnuSearchProduct) {
                        LayoutInflater aInflator = getLayoutInflater();
                        View alertLayout = aInflator.inflate(R.layout.product_list_filter, null);

                        Spinner productCombo = (Spinner) alertLayout.findViewById(R.id.productComboId);
                        Spinner providerCombo = (Spinner) alertLayout.findViewById(R.id.providerComboId);

                        String[] products = new String[]{"محصول را انتخاب کنید", "پسته", "مغز پسته"};
                        ArrayAdapter<String> productComboAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, products);

                        String[] providers = new String[]{"تامین کننده را انتخاب کنید", "سوپرناتس"};
                        ArrayAdapter<String> providerComboAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, providers);

                        productCombo.setAdapter(productComboAdapter);
                        providerCombo.setAdapter(providerComboAdapter);
                        previousSelectedBottomNavigationView =menuItem.getItemId();
                        new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                                .setTitle(getString(R.string.common_search))
                                .setCancelable(true)
                                .setView(alertLayout)
                                .setPositiveButton(getString(R.string.common_search), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Toast.makeText(mContext, "Yes Button Clicked!", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .create()
                                .show();
                        return true;
                    } else if (menuItem.getItemId() == R.id.mnuAddProduct && previousSelectedBottomNavigationView !=R.id.mnuAddProduct) {
                        Intent intent = new Intent(mContext, ProductInfoForSaveActivity.class);
                        intent.putExtra("previousSelectedBottomNavigationView",menuItem.getItemId());
                        startActivity(intent);
                        return true;
                    } else if (menuItem.getItemId() == R.id.mnuMyProfile && previousSelectedBottomNavigationView !=R.id.mnuMyProfile) {
                        Intent intent = new Intent(mContext, UserProfileActivity.class);
                        intent.putExtra("previousSelectedBottomNavigationView",menuItem.getItemId());
                        startActivity(intent);
                        return true;
                    }else if (menuItem.getItemId() == R.id.mnuMainHome && previousSelectedBottomNavigationView !=R.id.mnuMainHome) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("previousSelectedBottomNavigationView",menuItem.getItemId());
                        startActivity(intent);
                        return true;
                    }


                    return true;
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView != null)
            if (bottomNavigationView.getSelectedItemId() == R.id.mnuMainHome) {
                super.onBackPressed();
            } else {
                bottomNavigationView.setSelectedItemId(R.id.mnuMainHome);
            }
        else
            super.onBackPressed();
    }
//protected abstract int getLayoutId();
}
