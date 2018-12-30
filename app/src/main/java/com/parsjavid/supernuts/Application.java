package com.parsjavid.supernuts;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.aakira.expandablelayout.Utils;
import com.parsjavid.supernuts.di.AppModule;
import com.parsjavid.supernuts.di.DaggerMainComponent;
import com.parsjavid.supernuts.di.ImageLoaderMoudle;
import com.parsjavid.supernuts.di.MainComponent;
import com.parsjavid.supernuts.di.NetModule;
import com.parsjavid.supernuts.helper.DataBaseHelper;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Amir khodabandeh on 2/11/16 AD.
 */
public class Application extends android.app.Application {

    public static Typeface font;
    public static Typeface fontBold;
    public static Animation in;
    public static Animation out;
    public static SQLiteDatabase database;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static String CategoriesFilterPointer = "";
    public static String CategoryId = "";
    //public static ExpertFeedItem expertItemCall = new ExpertFeedItem();
    public static String CallId = "";
    public static Activity activity;
    public static MainComponent mainComponent;
    public static MainComponent mainComponent2;
    public static MainComponent mainComponent3;

    public static ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public static MainComponent getmainComponent() {
        return mainComponent;
    }

    public static MainComponent getmainComponentGtc() {
        return mainComponent2;
    }

    public static MainComponent getmainVideo() {
        return mainComponent3;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mainComponent = DaggerMainComponent.builder()
                .imageLoaderMoudle(new ImageLoaderMoudle(this))
                .netModule(new NetModule(BuildConfig.BASEURL))
                .appModule(new AppModule(this))
                .build();

        mainComponent2 = DaggerMainComponent.builder()
                .imageLoaderMoudle(new ImageLoaderMoudle(this))
                .netModule(new NetModule(BuildConfig.BASEURLGTC))
                .appModule(new AppModule(this))
                .build();

        mainComponent3 = DaggerMainComponent.builder()
                .imageLoaderMoudle(new ImageLoaderMoudle(this))
                .netModule(new NetModule("http://sabzehmeydoon.com/"))
                .appModule(new AppModule(this))
                .build();
        /*"https://ws.api.video/"*/
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMedium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        in = AnimationUtils.loadAnimation(this,
                R.anim.zoom_in);

        out = AnimationUtils.loadAnimation(this,
                R.anim.zoom_out);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        try {
            DataBaseHelper db = new DataBaseHelper(getApplicationContext()) {
                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                    String DB_PATH = Environment.getDataDirectory() + "/data/" + getBaseContext().getPackageName() + "/databases/EmdadKeshavarz.db";
                    getBaseContext().deleteDatabase(DB_PATH);
                }

                @Override
                public void onCreate(SQLiteDatabase db) {
                }
            };
            db.createdatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }


        String path = Environment.getDataDirectory() + "/data/" + getPackageName() + "/databases/EmdadKeshavarz.db";
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        font = Typeface.createFromAsset(this.getAssets(), "fonts/IRANSansMedium.ttf");
        fontBold = Typeface.createFromAsset(this.getAssets(), "fonts/IRANSansBold.ttf");

    }
}
