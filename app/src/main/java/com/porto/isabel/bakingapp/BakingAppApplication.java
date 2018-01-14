package com.porto.isabel.bakingapp;

import android.app.Application;

import com.porto.isabel.bakingapp.di.AppComponent;
import com.porto.isabel.bakingapp.di.AppModule;
import com.porto.isabel.bakingapp.di.DaggerAppComponent;
import com.porto.isabel.bakingapp.di.NetworkModule;

import timber.log.Timber;

public class BakingAppApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = initDagger(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    private AppComponent initDagger(BakingAppApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .networkModule(new NetworkModule())
                .build();
    }
}
