package com.nextdroidslabs.ajaystha;

import android.app.Application;

import com.nextdroidslabs.ajaystha.dagger.ApplicationModule;
import com.nextdroidslabs.ajaystha.dagger.component.ApplicationComponent;
import com.nextdroidslabs.ajaystha.dagger.component.DaggerApplicationComponent;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public class TinyChatApplication extends Application {
    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    protected void initComponent() {
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return mAppComponent;
    }
}
