package com.parsjavid.supernuts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.di.HSH;

public class UserPreferenceRegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preference_register);

        if (Application.preferences.getString(getString(R.string.mobile), "0").equals("0")) {
            startActivity(new Intent(UserPreferenceRegisterActivity.this, LoginActivity.class));
            finish();
        } else if (!Application.preferences.getString(getString(R.string.preference_PersonType), "0").equals("0")) {
            startActivity(new Intent(UserPreferenceRegisterActivity.this, MainActivity.class));
            finish();
        }

    }

    /**
     * save user preference like person type and main products that he act
     * @param view
     */
    public void saveUserPreferenceRegisterInfo(View view) {
        CheckBox chkWoleSaler= (CheckBox)findViewById(R.id.chk_wholesaler);
        CheckBox chkOmdeBuyer= (CheckBox)findViewById(R.id.chk_omde_buyer);
        CheckBox chkNormalBuyer= (CheckBox)findViewById(R.id.chk_normal_buyer);

        CheckBox chkPistachio= (CheckBox)findViewById(R.id.chk_pistachio);
        CheckBox chkPistachioNuts= (CheckBox)findViewById(R.id.chk_pistachio_nuts);
        CheckBox chkWalnut= (CheckBox)findViewById(R.id.chk_walnut);
        CheckBox chkAlmond= (CheckBox)findViewById(R.id.chk_almond);

        String personType= chkWoleSaler.isChecked()+","+chkOmdeBuyer.isChecked()+","+chkNormalBuyer.isChecked();
        String products= chkPistachio.isChecked()+","+chkPistachioNuts.isChecked()+","+chkWalnut.isChecked()+","+chkAlmond.isChecked();


        HSH.getInstance().editor(getString(R.string.preference_PersonType), personType);
        HSH.getInstance().editor(getString(R.string.preference_Products), products);

        HSH.getInstance().onOpenPage(UserPreferenceRegisterActivity.this, MainActivity.class);
        finish();
    }
}
