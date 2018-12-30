package com.parsjavid.supernuts.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity{

    Retrofit retrofit;
    public static EditText et_mobile,et_code;
    private FloatingActionButton fab;
    private TextView txt_timer;
    private TextInputLayout input_layout_code;
    private ProgressBar progressBar;
    private Map<String,String> params=new HashMap<>();
    private TextView txt_register;
    private Button bt_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getmainComponent().Inject(this);
    }
}
