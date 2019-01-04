package com.parsjavid.supernuts.di;

import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.activities.LoginActivity;
import com.parsjavid.supernuts.asynkTasks.RefreshTokenAsynkTask;


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

}
