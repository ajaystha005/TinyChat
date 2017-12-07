package com.nextdroidslabs.ajaystha.tinychat;

import com.nextdroidslabs.ajaystha.NDLPresenter;
import com.nextdroidslabs.ajaystha.NDLView;
import com.nextdroidslabs.ajaystha.pojo.Message;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public interface TinyChatContract {

    interface View extends NDLView {

        void displayMessage(String message);

        void populateMessage(Message message);

        void displayStatus(String status);
    }

    interface Presenter extends NDLPresenter {

        void initialize();

        void sendMessage(String message);
    }
}
