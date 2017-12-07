package com.nextdroidslabs.ajaystha.data.respository;

import com.nextdroidslabs.ajaystha.data.sharedpreference.NDLSharedPreferences;
import com.nextdroidslabs.ajaystha.pojo.Message;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public interface MessageRepository extends NDLSharedPreferences {

    void addMessageToQueue(Message message);

    Message getMessageFromQueue();

    boolean isQueueEmpty();
}
