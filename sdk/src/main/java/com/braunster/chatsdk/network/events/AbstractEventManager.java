package com.braunster.chatsdk.network.events;

import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;

import org.jdeferred.Deferred;

public abstract class AbstractEventManager {

    public abstract void userOn(final BUser user);

    public abstract void userOff(BUser user);

    /**
     * Handle user meta change.
     **/
    public abstract void userMetaOn(String userID, Deferred<Void, Void, Void> promise);

    /**
     * Stop handling user meta change.
     **/
    public abstract void userMetaOff(String userID);

    public abstract void threadUsersAddedOn(String threadId);

    public abstract void threadUsersAddedOff(String threadId);

    public abstract void messagesOn(String threadId, Deferred<BThread, Void , Void> deferred);

    public abstract void messagesOff(String threadId);

    public abstract void threadOn(String threadId, Deferred<BThread, Void, Void> deferred);

    public abstract void threadOff(String threadId);





    public abstract boolean isListeningToThread(String entityID);

    public abstract void addEvent(Event appEvents);

    /** Removes an app event by tag.*/
    public abstract boolean removeEventByTag(String tag);

    /** Check if there is a AppEvent listener with the currnt tag, Could be AppEvent or one of his child(MessageEventListener, ThreadEventListener, UserEventListener).
     * @return true if found.*/
    public abstract boolean isEventTagExist(String tag);


    /**
     * Removes all the events from the event manger.
     **/
    public abstract void removeAll();

}
