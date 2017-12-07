package com.nextdroidslabs.ajaystha.dagger;

import android.content.Context;

import com.nextdroidslabs.ajaystha.dagger.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ajayshrestha on 11/27/17.
 */

@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @ApplicationScope
    @Provides
    public Context provideContext() {
        return mContext;
    }
}
