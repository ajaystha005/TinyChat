package com.nextdroidslabs.ajaystha.dagger.component;

import com.nextdroidslabs.ajaystha.dagger.scope.ActivityScope;
import com.nextdroidslabs.ajaystha.tinychat.TinyChatActivity;
import com.nextdroidslabs.ajaystha.tinychat.TinyChatModule;

import dagger.Subcomponent;

/**
 * Created by ajayshrestha on 11/27/17.
 */
@ActivityScope
@Subcomponent(modules = TinyChatModule.class)
public interface TinyChatComponent {
    void inject(TinyChatActivity tinyChatActivity);
}
