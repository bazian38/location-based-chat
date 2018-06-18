package com.braunster.chatsdk.network.events;

import com.braunster.chatsdk.interfaces.AppEvents;

import java.util.Map;

public abstract class ThreadEventListener extends Event implements AppEvents{
    public ThreadEventListener(String tag, String threadEntityID){
        super(tag, threadEntityID);
    }

    @Override
    public abstract boolean onThreadDetailsChanged(String threadId);

    @Override
    public abstract boolean onThreadUsersTypingChanged(String threadId, Map<String,String> usersTyping);

    @Override
    public boolean onUserAddedToThread(String threadId, String userId) {
        return false;
    }
}
