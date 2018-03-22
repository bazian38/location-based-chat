package com.braunster.chatsdk.network;

import android.app.Activity;
import android.content.Context;

import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.interfaces.GeoInterface;
import com.braunster.chatsdk.interfaces.GeoThreadInterface;

/**
 * Created by Erk on 05.04.2016.
 */
public abstract class AbstractGeoFireManager {

    public abstract void setGeoDelegate(GeoInterface delegate);

    public abstract void setThreadDelegate(GeoThreadInterface delegate);

    public abstract void start(Context ctx, Activity activity);

    public abstract void startForThreads(Context ctx, Activity activity);

    public abstract void setThreadLocation(Context ctx, Activity activity, BThread thread);
}
