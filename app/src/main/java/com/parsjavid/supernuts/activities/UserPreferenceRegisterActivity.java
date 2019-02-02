package com.parsjavid.supernuts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.di.HSH;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.models.ApiSuccess;
import com.parsjavid.supernuts.models.Customer;
import com.parsjavid.supernuts.models.ProductGroup;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserPreferenceRegisterActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    EditText systemUserName;
    EditText systemUserFamily;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preference_register);
        Application.getmainComponent().Inject(this);
        systemUserName = findViewById(R.id.systemUserName);
        systemUserFamily = findViewById(R.id.systemUserFamily);

        if (Application.preferences.getString(getString(R.string.mobile), "0").equals("0")) {
            startActivity(new Intent(UserPreferenceRegisterActivity.this, LoginActivity.class));
            finish();
        }
        else if (!Application.preferences.getString(getString(R.string.preference_PersonType), "0").equals("0")) {
            startActivity(new Intent(UserPreferenceRegisterActivity.this, MainActivity.class));
            finish();
        }

    }

    /**
     * save user preference like person type and main products that he act
     *
     * @param view
     */
    public void saveUserPreferenceRegisterInfo(View view) {
        CheckBox chkWoleSaler = (CheckBox) findViewById(R.id.chk_wholesaler);
        CheckBox chkOmdeBuyer = (CheckBox) findViewById(R.id.chk_omde_buyer);
        CheckBox chkNormalBuyer = (CheckBox) findViewById(R.id.chk_normal_buyer);

        CheckBox chkPistachio = (CheckBox) findViewById(R.id.chk_pistachio);
        CheckBox chkPistachioNuts = (CheckBox) findViewById(R.id.chk_pistachio_nuts);
        CheckBox chkWalnut = (CheckBox) findViewById(R.id.chk_walnut);
        CheckBox chkAlmond = (CheckBox) findViewById(R.id.chk_almond);

        String personType = chkWoleSaler.isChecked() + "," + chkOmdeBuyer.isChecked() + "," + chkNormalBuyer.isChecked();
        String products = (chkPistachio.isChecked() ? ProductGroup.Pistachio : "") + "," +
                (chkPistachioNuts.isChecked() ? ProductGroup.Pistachio_Kernel : "") + "," +
                (chkWalnut.isChecked() ? ProductGroup.Walnut : "") + "," +
                (chkAlmond.isChecked() ? ProductGroup.Almond : "");

        boolean isProductSelected = chkPistachio.isChecked() || chkPistachioNuts.isChecked() || chkWalnut.isChecked() || chkAlmond.isChecked();

        HSH.getInstance().editor(getString(R.string.preference_PersonType), personType);
        HSH.getInstance().editor(getString(R.string.preference_Products), products);

        String mobile = Application.preferences.getString(getString(R.string.mobile), "0");
        String token = Application.preferences.getString(getString(R.string.ApToken), "0");

        String user_name = systemUserName.getText().toString();
        String user_family = systemUserFamily.getText().toString();

        if (user_name.trim().equals(""))
            systemUserName.setError("لطفا نام را وارد نمایید.");
        else if (!isProductSelected) {
            HSH.getInstance().showToast(UserPreferenceRegisterActivity.this, "حداقل یک محصول را انتخاب نمایید", HSH.MESSAGE_TYPE_ERROR);
        } else {
            Customer customer = new Customer();
            customer.setName(user_name);
            customer.setFamily(user_family);
            customer.setMobile(mobile);
            customer.setToken(token);
            customer.setIsMajorBuyer(chkOmdeBuyer.isChecked());
            customer.setIsWholeSaler(chkWoleSaler.isChecked());
            customer.setIsProvider(chkWoleSaler.isChecked());

            Map<String,String> data=new HashMap<>();
            data.put("name",user_name);
            data.put("family",user_family);
            data.put("mobile",mobile);
            data.put("token",token);
            data.put("isMajorBuyer",String.valueOf(customer.getIsMajorBuyer()));
            data.put("isWholeSaler",String.valueOf(customer.getIsWholeSaler()));
            data.put("isProvider",String.valueOf(customer.getIsProvider()));


            Call<ApiSuccess> apiSuccessCall = retrofit.create(ApiInterface.class).SaveCustomer(data);
            apiSuccessCall.enqueue(new Callback<ApiSuccess>() {
                @Override
                public void onResponse(Call<ApiSuccess> call, Response<ApiSuccess> response) {
                    if (response.isSuccessful()) {
                        ApiSuccess apiSuccess = response.body();
                        if(apiSuccess.getErrorMessage()!=null && !apiSuccess.getErrorMessage().trim().equals("")){
                            HSH.showToast(UserPreferenceRegisterActivity.this,apiSuccess.getErrorMessage(),HSH.MESSAGE_TYPE_ERROR);
                        }else{
                            HSH.showToast(UserPreferenceRegisterActivity.this,apiSuccess.getMessage());
                            HSH.getInstance().onOpenPage(UserPreferenceRegisterActivity.this, MainActivity.class);
                            finish();
                        }
                    }else{
                        HSH.showToast(UserPreferenceRegisterActivity.this,getString(R.string.common_saveProblem_message),HSH.MESSAGE_TYPE_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<ApiSuccess> call, Throwable t) {
                    HSH.showToast(UserPreferenceRegisterActivity.this,getString(R.string.common_saveProblem_message),HSH.MESSAGE_TYPE_ERROR);
                }
            });


        }
    }
}
