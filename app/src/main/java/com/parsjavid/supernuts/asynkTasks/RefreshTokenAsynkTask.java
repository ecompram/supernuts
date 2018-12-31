package com.parsjavid.supernuts.asynkTasks;


import android.content.Context;
import android.os.Build;

import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.R;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RefreshTokenAsynkTask {

    @Inject
    Retrofit retrofit;
    private Context cn;
    private String token;

    public RefreshTokenAsynkTask(Context cn, String token) {
        this.cn = cn;
        this.token = token;
        Application.getmainComponent().Inject(this);
    }

    public void sendRegistrationToServer() {

        Map<String, String> params = new HashMap<>();
        params.put("UserType", "Applicant");
        params.put(cn.getString(R.string.UserId), Application.preferences.getString(cn.getString(R.string.UserId), "0"));
        params.put("RefreshToken", token);
        params.put("AndroidVersion", getAndroidVersion());
        try {
            params.put("VersionName", cn.getPackageManager().getPackageInfo(cn.getPackageName(), 0).versionName);
        } catch (Exception e) {
        }

        Call<ResponseBody> call =
                retrofit.create(ApiInterface.class).RefreshToken(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sendRegistrationToServer();
            }
        });
    }

    public String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return sdkVersion + " (" + release + ")";
    }
}


