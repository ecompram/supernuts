package com.parsjavid.supernuts.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.di.HSH;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
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
        setContentView(R.layout.activity_login);
        Application.activity=LoginActivity.this;
        DeclareElements();
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    bt_go.setEnabled(false);
                    bt_go.setBackgroundResource(R.drawable.bt_shape);
                }else{
                    bt_go.setEnabled(true);
                    bt_go.setBackgroundResource(R.drawable.press_button_background_green);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED,null);
        finish();
    }
    public void clickRegisterLayout(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this,fab,fab.getTransitionName());
            //startActivityForResult(new Intent(this, RegisterActivity.class), 123, options.toBundle());
        }else{
            //startActivityForResult(new Intent(this, RegisterActivity.class), 123);
        }
    }
    public void clickLogin(final View view ){
        String mobile = et_mobile.getText().toString();
        if(mobile.equals("") || !mobile.startsWith("09") || mobile.length()!=11){
            HSH.getInstance().showtoast(LoginActivity.this,"لطفا شماره موبایل معتبر وارد نمایید.");
        }else{

        }
    }

    private void DeclareElements(){
        fab=findViewById(R.id.fab);
        input_layout_code=findViewById(R.id.input_layout_code);
        et_mobile=findViewById(R.id.et_mobile);
        et_code=findViewById(R.id.et_code);
        et_mobile.setTypeface(Application.font);
        et_code.setTypeface(Application.font);
        progressBar=findViewById(R.id.progressBar);
        txt_timer=findViewById(R.id.txt_timer);
        bt_go=findViewById(R.id.bt_go);

        txt_register=findViewById(R.id.txt_register);
        txt_register.setMovementMethod(LinkMovementMethod.getInstance());

        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==6){
                    progressBar.setVisibility(View.VISIBLE);
                    bt_go.setEnabled(false);
                    Map<String,String> params=new HashMap<>();
                    params.put(getString(R.string.mobile),et_mobile.getText().toString().trim());
                    params.put(getString(R.string.SmsCode),et_mobile.getText().toString().trim());
                    CheckPhoneNumber(bt_go,params);
                }
            }
        });
    }
    private void CheckPhoneNumber(final View v,final Map<String,String> params){
        //Call<ResponseBody> call=retrofit.create(ApiInterface.class)
    }
}
