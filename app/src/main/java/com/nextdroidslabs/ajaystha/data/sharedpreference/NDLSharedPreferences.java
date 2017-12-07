package com.nextdroidslabs.ajaystha.data.sharedpreference;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public interface NDLSharedPreferences {

    long getLastSyncedTime();

    void saveLastSyncedTime(long lastSyncTime);
}
