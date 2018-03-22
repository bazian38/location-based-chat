package com.braunster.chatsdk.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.braunster.chatsdk.BuildConfig;
import com.braunster.chatsdk.R;
import com.braunster.chatsdk.Utils.volley.VolleyUtils;
import com.braunster.chatsdk.dao.core.DaoCore;
import com.braunster.chatsdk.interfaces.GeoInterface;
import com.bugsense.trace.BugSenseHandler;

import org.apache.commons.lang3.StringUtils;

public class BNetworkManager {

    private static final String CHAT_SDK_SHRED_PREFS = "ChatSDK_Prefs";
    public static final boolean BUGSENSE_ENABLED = false, PushEnabledDefaultValue = true;

    public static SharedPreferences preferences;

    private static BNetworkManager instance;

    private AbstractNetworkAdapter networkAdapter;

    private GeoInterface geoDelegate;

    private static Context context;

    public static void init(Context ctx){
        context = ctx;

        preferences = ctx.getSharedPreferences(CHAT_SDK_SHRED_PREFS, Context.MODE_PRIVATE);
        VolleyUtils.init(ctx);
        DaoCore.init(ctx);

        //Bug Sense
        if (BNetworkManager.BUGSENSE_ENABLED && StringUtils.isNotEmpty( context.getString(R.string.bug_sense_key) )) {
            BugSenseHandler.initAndStartSession(ctx, context.getString(R.string.bug_sense_key));
            BugSenseHandler.addCrashExtraData("Version", BuildConfig.VERSION_NAME);
        }
    }

    public static BNetworkManager sharedManager(){
        if (instance == null) {
            instance = new BNetworkManager();
        }
        return instance;
    }

    public void setNetworkAdapter(AbstractNetworkAdapter networkAdapter) {
        this.networkAdapter = networkAdapter;
    }

    public AbstractNetworkAdapter getNetworkAdapter() {
        return networkAdapter;
    }

    //TODO
/*    *//* Always safe to call*//*
    public static SharedPreferences getUserPrefs(String entityId){
        return context.getSharedPreferences(entityId, Context.MODE_PRIVATE);
    }
    *//* Safe to call after login.*//*
    public static SharedPreferences getCurrentUserPrefs(){
        return context.getSharedPreferences(sharedManager().getNetworkAdapter().currentUserModel().getEntityID(), Context.MODE_PRIVATE);
    }

    public void setGeoDelegate(GeoInterface delegate) {
        geoDelegate = delegate;
    }

    public GeoInterface getGeoDelegate() {
        return geoDelegate;
    }*/
}
