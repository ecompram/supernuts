package com.parsjavid.supernuts.di;

import com.parsjavid.supernuts.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KingStar on 3/2/2018.
 */

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @MainScope
    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}