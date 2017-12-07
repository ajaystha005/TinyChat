package com.nextdroidslabs.ajaystha.tinychat;

import com.nextdroidslabs.ajaystha.dagger.scope.ActivityScope;
import com.nextdroidslabs.ajaystha.data.respository.MessageRepository;
import com.nextdroidslabs.ajaystha.data.respository.MessageRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ajayshrestha on 11/27/17.
 */
@Module
public class TinyChatModule {

    private TinyChatContract.View mView;

    public TinyChatModule(TinyChatContract.View view) {
        this.mView = view;
    }

    @Provides
    @ActivityScope
    public TinyChatContract.View provideView() {
        return this.mView;
    }

    @Provides
    @ActivityScope
    public TinyChatContract.Presenter providePresenter(TinyChatPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    public MessageRepository provideMessageRepository(MessageRepositoryImpl messageRepository) {
        return messageRepository;
    }
}
