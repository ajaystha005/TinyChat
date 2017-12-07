package com.nextdroidslabs.ajaystha.data.respository;

import com.nextdroidslabs.ajaystha.data.sharedpreference.NDLSharedPreferences;
import com.nextdroidslabs.ajaystha.pojo.Message;

import java.util.LinkedList;

import javax.inject.Inject;

/**
 * Created by ajayshrestha on 11/27/17.
 */

/**
 * This is message repository responsible for store offline  message
 */
public class MessageRepositoryImpl implements MessageRepository {

    private LinkedList<Message> mMessageQueue = new LinkedList<>();

    private NDLSharedPreferences mSharedPreferencesHelper;


    @Inject
    public MessageRepositoryImpl(NDLSharedPreferences sharedPreferencesHelper) {
        this.mSharedPreferencesHelper = sharedPreferencesHelper;
    }

    @Override
    public void addMessageToQueue(Message message) {
        mMessageQueue.add(message);
    }

    @Override
    public Message getMessageFromQueue() {
        return mMessageQueue.poll();
    }

    @Override
    public boolean isQueueEmpty() {
        return mMessageQueue.isEmpty();
    }

    @Override
    public long getLastSyncedTime() {
        return mSharedPreferencesHelper.getLastSyncedTime();
    }

    @Override
    public void saveLastSyncedTime(long lastSyncTime) {
        mSharedPreferencesHelper.saveLastSyncedTime(lastSyncTime);
    }
}
