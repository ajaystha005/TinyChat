package com.nextdroidslabs.ajaystha.socket;

import com.nextdroidslabs.ajaystha.pojo.Message;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public interface TinySocketContract {
    void init(MessageListener messageListener);

    void connect();

    void disconnect();

    void sendMessage(Message message);

    void syncMessage(long lastSyncTime);

    boolean isConnected();


    interface MessageListener {

        void onMessageReceived(Message message);

        void onConnected();

        void onDisconnected();

        void onSendingFailed(Message message);

        void onError();

        void updateLastSyncedTime();
    }
}
