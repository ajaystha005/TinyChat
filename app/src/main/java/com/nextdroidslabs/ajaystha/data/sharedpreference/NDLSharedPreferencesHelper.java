package com.nextdroidslabs.ajaystha.data.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.nextdroidslabs.ajaystha.constant.PrefConstant;

import javax.inject.Inject;

/**
 * Created by ajayshrestha on 11/27/17.
 */

/**
 * SharedPreferencesHelper to store data
 */
public class NDLSharedPreferencesHelper implements NDLSharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public NDLSharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(PrefConstant.PRE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public long getLastSyncedTime() {
        return mSharedPreferences.getLong(PrefConstant.PREF_LAST_SYNC_TIME, 0);
    }

    /**
     * Save Last Synced Time with server
     *
     * @param lastSyncTime
     */
    @Override
    public void saveLastSyncedTime(long lastSyncTime) {
        mSharedPreferences.edit().putLong(PrefConstant.PREF_LAST_SYNC_TIME, lastSyncTime).apply();
    }
}
