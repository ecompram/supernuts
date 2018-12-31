package com.parsjavid.supernuts.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.google.firebase.iid.FirebaseInstanceId;
import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.di.HSH;
import com.parsjavid.supernuts.interfaces.ApiInterface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    public static EditText et_mobile, et_code;
    private FloatingActionButton fab;
    private TextView txt_timer;
    private TextInputLayout input_layout_code;
    private ProgressBar progressBar;
    private Map<String, String> params = new HashMap<>();
    private TextView txt_register;
    private Button bt_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getmainComponent().Inject(this);
        setContentView(R.layout.activity_login);
        Application.activity = LoginActivity.this;
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
                if (s.length() == 0) {
                    bt_go.setEnabled(false);
                    bt_go.setBackgroundResource(R.drawable.bt_shape);
                } else {
                    bt_go.setEnabled(true);
                    bt_go.setBackgroundResource(R.drawable.press_button_background_green);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED, null);
        finish();
    }

    public void clickRegisterLayout(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            //startActivityForResult(new Intent(this, RegisterActivity.class), 123, options.toBundle());
        } else {
            //startActivityForResult(new Intent(this, RegisterActivity.class), 123);
        }
    }

    public void clickLogin(final View view) {
        String mobile = et_mobile.getText().toString();
        if (mobile.equals("") || !mobile.startsWith("09") || mobile.length() != 11) {
            HSH.getInstance().showtoast(LoginActivity.this, "لطفا شماره موبایل معتبر وارد نمایید.");
        } else {
            final String PN = ((EditText) findViewById(R.id.et_mobile)).getText().toString().trim();
            final SweetAlertDialog dialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.NORMAL_TYPE);
            dialog.setTitleText(PN);
            dialog.setContentText("شماره موبایل صحیح است؟");
            dialog.setConfirmText("بله");
            dialog.setCancelText("خیر");
            dialog.setConfirmClickListener((SweetAlertDialog sDialog) -> {
                et_mobile.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                view.setEnabled(false);
                params.put(getString(R.string.mobile), PN);

                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
                    params.put(getString(R.string.Token), instanceIdResult.getToken());
                });
                params.put(getString(R.string.Type), "Login");
                SendPhoneNumber(view);
                dialog.dismiss();
            });
            dialog.setCancelClickListener((SweetAlertDialog sweetAlertDialog) -> dialog.dismissWithAnimation());
            dialog.setCancelable(true);
            HSH.getInstance().dialog(dialog);
        }
    }

    private void SendPhoneNumber(final View v) {
        try {
            if (null == params.get(getString(R.string.Token)))
                params.remove(getString(R.string.Token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<ResponseBody> call = retrofit.create(ApiInterface.class).EmdadApplicantRegister(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        txt_register.setVisibility(View.INVISIBLE);
                        findViewById(R.id.txt_w8).setVisibility(View.VISIBLE);
                        txt_timer.setVisibility(View.VISIBLE);
                        txt_timer.setEnabled(false);
                        final String FORMAT = "%02d:%02d";
                        new CountDownTimer(120000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                txt_timer.setText("زمان باقی مانده (" + String.format(FORMAT,
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                        )) + ")");
                            }

                            @Override
                            public void onFinish() {
                                txt_timer.setBackgroundResource(R.drawable.press_button_background_green);
                                txt_timer.setText("ارسال مجدد");
                                txt_timer.setEnabled(false);
                                txt_timer.setVisibility(View.VISIBLE);
                                txt_timer.setOnClickListener(view -> SendPhoneNumber(v));
                            }
                        }.start();

                        try {
                            progressBar.setVisibility(View.GONE);
                            HSH.getInstance().display(LoginActivity.this, input_layout_code);
                            et_code.setEnabled(true);
                            v.setEnabled(true);
                            ((Button) v).setText("تایید");
                            v.setOnClickListener(v -> {
                                String s = et_code.getText().toString().trim();
                                if (s.equals(""))
                                    HSH.getInstance().showtoast(LoginActivity.this, "لطفا کد تایید دریافتی را وارد نمایید");
                                else {
                                    progressBar.setVisibility(View.VISIBLE);
                                    v.setEnabled(false);
                                    Map<String, String> params = new HashMap<>();
                                    params.put(getString(R.string.mobile), et_mobile.getText().toString().trim());
                                    params.put(getString(R.string.SmsCode), et_code.getText().toString().trim());
                                    CheckPhoneNumber(v, params);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    v.setEnabled(true);
                    et_mobile.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    try {
                        switch (response.code()) {
                            case 400:
                                HSH.getInstance().showtoast(LoginActivity.this, "ابتدا عضو شوید");
                                break;
                            case 500:
                                HSH.getInstance().showtoast(LoginActivity.this, "لطفا دقایقی بعد مجددا تلاش کنید");
                                break;
                            default:
                                HSH.getInstance().showtoast(LoginActivity.this, "خطا در برقراری ارتباط با سرور");
                                break;
                        }
                    } catch (Exception e) {
                        HSH.getInstance().showtoast(LoginActivity.this, "خطا در ارتباط با سرور");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                SendPhoneNumber(v);
            }
        });

    }

    private void DeclareElements() {
        fab = findViewById(R.id.fab);
        input_layout_code = findViewById(R.id.input_layout_code);
        et_mobile = findViewById(R.id.et_mobile);
        et_code = findViewById(R.id.et_code);
        et_mobile.setTypeface(Application.font);
        et_code.setTypeface(Application.font);
        progressBar = findViewById(R.id.progressBar);
        txt_timer = findViewById(R.id.txt_timer);
        bt_go = findViewById(R.id.bt_go);

        txt_register = findViewById(R.id.txt_register);
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
                if (s.length() == 6) {
                    progressBar.setVisibility(View.VISIBLE);
                    bt_go.setEnabled(false);
                    Map<String, String> params = new HashMap<>();
                    params.put(getString(R.string.mobile), et_mobile.getText().toString().trim());
                    params.put(getString(R.string.SmsCode), et_mobile.getText().toString().trim());
                    CheckPhoneNumber(bt_go, params);
                }
            }
        });
    }

    private void CheckPhoneNumber(final View v, final Map<String, String> params) {
        Call<ResponseBody> call = retrofit.create(ApiInterface.class).Verification(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject result = null;
                        result = new JSONObject(response.body().string().trim());
                        Integer Type = Integer.parseInt(result.getString(getString(R.string.Type)));
                        if (Type == 0) {
                            String Message = result.getString("Message");
                            HSH.getInstance().showtoast(LoginActivity.this, Message);
                            v.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            try {
                                HSH.getInstance().editor(getString(R.string.Materialintro), "True");
                                HSH.getInstance().editor(getString(R.string.UserId), result.getString(getString(R.string.Type)));
                                HSH.getInstance().editor(getString(R.string.mobile), params.get(getString(R.string.mobile)));
                                HSH.getInstance().editor(getString(R.string.ApToken), result.getString(getString(R.string.ApToken)));

                                HSH.getInstance().onOpenPage(LoginActivity.this, MainActivity.class);
                                finish();
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    HSH.getInstance().showtoast(LoginActivity.this, "خطا در برقراری ارتباط با سرور");
                    v.setEnabled(true);
                    et_mobile.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CheckPhoneNumber(v, params);
            }
        });

    }
}
