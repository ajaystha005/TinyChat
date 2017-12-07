package com.nextdroidslabs.ajaystha.tinychat;

import com.nextdroidslabs.ajaystha.R;
import com.nextdroidslabs.ajaystha.data.respository.MessageRepository;
import com.nextdroidslabs.ajaystha.pojo.Message;
import com.nextdroidslabs.ajaystha.socket.TinySocketContract;

import javax.inject.Inject;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public class TinyChatPresenter implements TinyChatContract.Presenter, TinySocketContract.MessageListener {

    private TinyChatContract.View mView;

    private TinySocketContract mSocket;

    private MessageRepository mMessageRepository;

    private boolean isConnected = true;

    @Inject
    public TinyChatPresenter(TinyChatContract.View view, TinySocketContract socket, MessageRepository messageRepository) {
        this.mView = view;
        this.mSocket = socket;
        this.mMessageRepository = messageRepository;
    }

    /**
     * Detach Presenter from View
     */
    @Override
    public void onDetach() {
        mSocket.disconnect();
    }

    /**
     * Init the Socket implementation
     */
    @Override
    public void initialize() {
        try {
            mSocket.init(this);
        } catch (RuntimeException ex) {
            mView.displayMessage("cant be null");
        }
    }

    /**
     * Send message via Protocol
     *
     * @param text
     */
    @Override
    public void sendMessage(String text) {
        mSocket.sendMessage(new Message(text));
    }

    /**
     * If message received from Server
     *
     * @param message
     */
    @Override
    public void onMessageReceived(Message message) {
        mView.populateMessage(message);
    }

    /**
     * If successfully connected
     */
    @Override
    public void onConnected() {
        isConnected = true;
        String message = mView.getContext().getString(R.string.connect);
        mView.displayMessage(message);
        mView.displayStatus(message);

        //try to Resend all the offline message
        if (!mMessageRepository.isQueueEmpty()) {
            while (isConnected && !mMessageRepository.isQueueEmpty()) {
                mSocket.sendMessage(mMessageRepository.getMessageFromQueue());
            }
        }

        //Sync message from server since last Sync
        mSocket.syncMessage(mMessageRepository.getLastSyncedTime());
    }

    /**
     * If Connection is disconnected
     */
    @Override
    public void onDisconnected() {
        String message = mView.getContext().getString(R.string.disconnect);
        mView.displayMessage(message);
        mView.displayStatus(message);
        isConnected = false;
    }

    /**
     * IF message sending failed, push to queue. When the app comes online, it will try to resend everything
     *
     * @param message
     */
    @Override
    public void onSendingFailed(Message message) {
        mMessageRepository.addMessageToQueue(message);

        mView.displayMessage("Message added to Queue");
    }

    @Override
    public void onError() {
        // mView.displayMessage("Error");
    }

    /**
     * Store the last sync time with server
     */
    @Override
    public void updateLastSyncedTime() {
        mMessageRepository.saveLastSyncedTime(System.currentTimeMillis());
    }
}
