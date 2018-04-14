package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.braunster.androidchatsdk.firebaseplugin.firebase.BChatcatNetworkAdapter;
import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.network.AbstractNetworkAdapter;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BNetworkManager;
import com.braunster.chatsdk.object.BError;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;
import org.mockito.Mock;
import org.mockito.Mockito;

import static com.braunster.chatsdk.network.BDefines.Prefs.AuthenticationID;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Instrumented test, which will execute on an Android device.
 */

@RunWith(AndroidJUnit4.class)
public class UnitInstrumentedTests {

    static private Context appContext;
    static private BChatcatNetworkAdapter adapter;

    @BeforeClass
    public static void setUpBeforeClass() {
        appContext = InstrumentationRegistry.getTargetContext();
        adapter = new BChatcatNetworkAdapter(appContext, true);
        BDefines.changeRootForTesting();
//        BNetworkManager.preferences = Mockito.mock(SharedPreferences.class);
    }

    @AfterClass
    public static void setUpAfterClass() {

//        BDefines.changeRootBack();
    }

    @Test
    public void registerTest() throws Exception {
        Map<String, Object> data = AbstractNetworkAdapter.getMap(
                new String[]{BDefines.Prefs.LoginTypeKey, BDefines.Prefs.LoginEmailKey, BDefines.Prefs.LoginPasswordKey},
                BDefines.BAccountType.Register, "agbaria2@post.bgu.ac.il", "123456");
        adapter.authenticateWithMap(data).done(new DoneCallback<Object>() {
            @Override
            public void onDone(Object result) {
                assertTrue((Boolean) result);
            }
        });
    }

    @Test
    public void loginTest() {
        Map<String, Object> unauthUser = AbstractNetworkAdapter.getMap(
                new String[]{BDefines.Prefs.LoginTypeKey, BDefines.Prefs.LoginEmailKey, BDefines.Prefs.LoginPasswordKey},
                BDefines.BAccountType.Password, "agbaria2@post.bgu.ac.il", "123456");

        Map<String, Object> fakeUser = AbstractNetworkAdapter.getMap(
                new String[]{BDefines.Prefs.LoginTypeKey, BDefines.Prefs.LoginEmailKey, BDefines.Prefs.LoginPasswordKey},
                BDefines.BAccountType.Password, "abcdefg@post.bgu.ac.il", "123456");

        Map<String, Object> realUser = AbstractNetworkAdapter.getMap(
                new String[]{BDefines.Prefs.LoginTypeKey, BDefines.Prefs.LoginEmailKey, BDefines.Prefs.LoginPasswordKey},
                BDefines.BAccountType.Password, "agbaria@post.bgu.ac.il", "123456");

        adapter.authenticateWithMap(unauthUser).fail(new FailCallback<BError>() {
            @Override
            public void onFail(BError result) {
                assertEquals(BError.Code.NO_VERIFY_EMAIL, result.code);
            }
        });

        adapter.authenticateWithMap(fakeUser).fail(new FailCallback<BError>() {

            @Override
            public void onFail(BError result) {
                assertEquals(BError.Code.AUTH_IN_PROCESS, result.code);
            }
        });

        adapter.authenticateWithMap(realUser).done(new DoneCallback<Object>() {
            @Override
            public void onDone(Object result) {
                assertTrue((Boolean) result);
            }
        });
    }

    @Test
    public void createThreadTest() {


        when(BNetworkManager.preferences.getString(AuthenticationID, "")).thenReturn("mDhnoQcfkOeIicnb50ToyXOGiMj1");

        adapter.createPublicThreadWithName("testThread").done(new DoneCallback<BThread>() {

            @Override
            public void onDone(BThread result) {
                assertTrue(result != null);
            }
        });


    }
}
