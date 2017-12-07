package com.nextdroidslabs.ajaystha.data;

import com.nextdroidslabs.ajaystha.dagger.scope.ApplicationScope;
import com.nextdroidslabs.ajaystha.data.sharedpreference.NDLSharedPreferences;
import com.nextdroidslabs.ajaystha.data.sharedpreference.NDLSharedPreferencesHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ajayshrestha on 11/27/17.
 */
@Module
public class DataModule {

    @Provides
    @ApplicationScope
    public NDLSharedPreferences provideSharePreferences(NDLSharedPreferencesHelper ogSharedPreferences) {
        return ogSharedPreferences;
    }
}
