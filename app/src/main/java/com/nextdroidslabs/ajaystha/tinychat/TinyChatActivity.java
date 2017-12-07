package com.nextdroidslabs.ajaystha.tinychat;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;

import com.nextdroidslabs.ajaystha.R;
import com.nextdroidslabs.ajaystha.TinyChatApplication;
import com.nextdroidslabs.ajaystha.databinding.ActivityTinyChatMainBinding;
import com.nextdroidslabs.ajaystha.pojo.Message;

import javax.inject.Inject;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public class TinyChatActivity extends AppCompatActivity implements TinyChatContract.View {

    @Inject
    TinyChatContract.Presenter mPresenter;

    private ActivityTinyChatMainBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TinyChatApplication) getApplication()).getComponent().plus(new TinyChatModule(this)).inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tiny_chat_main);
        mBinding.setHandler(this);

        initUI();

        mPresenter.initialize();

    }

    private void initUI() {
        mBinding.tvResponse.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * Display any info message to user
     *
     * @param message
     */
    @Override
    public void displayMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TinyChatActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Display Message on Received from server
     *
     * @param message
     */
    @Override
    public void populateMessage(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message != null) {
                    mBinding.tvResponse.setText(message.getText() + "\n" + mBinding.tvResponse.getText());
                }
            }
        });
    }

    /**
     * Display status of Connection - Connected / Disconnected
     *
     * @param status
     */
    @Override
    public void displayStatus(String status) {
        mBinding.status.setText(status);
    }

    /**
     * IF user click send message button
     */
    public void onClickSend() {
        String message = mBinding.etMessage.getText().toString();
        if (!message.isEmpty()) {
            mPresenter.sendMessage(message);
            clearTextField();
        } else {
            displayMessage("Message can't be null");
        }
    }

    private void clearTextField() {
        mBinding.etMessage.setText("");
    }
}
