package com.parsjavid.supernuts.di;

import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.activities.LoginActivity;
import com.parsjavid.supernuts.activities.ProductDetailInfoActivity;
import com.parsjavid.supernuts.activities.ProductInfoForSaveActivity;
import com.parsjavid.supernuts.activities.UserPreferenceRegisterActivity;
import com.parsjavid.supernuts.asynkTasks.RefreshTokenAsynkTask;
import com.parsjavid.supernuts.fragments.UserProfileProductsFragment;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by KingStar on 3/2/2018.
 */
@Singleton
@MainScope
@Component(modules = {AppModule.class, ImageLoaderMoudle.class, NetModule.class})
public interface MainComponent {

    void Inject(LoginActivity loginActivity);
    void Inject(RefreshTokenAsynkTask refreshTokenAsynkTask);
    void Inject(MainActivity mainActivity);
    void Inject(ProductDetailInfoActivity productDetailInfoActivity);
    void Inject(UserPreferenceRegisterActivity userPreferenceRegisterActivity);
    void Inject(ProductInfoForSaveActivity productInfoForSaveActivity);

    void Inject(UserProfileProductsFragment userProfileProductsFragment);

}
