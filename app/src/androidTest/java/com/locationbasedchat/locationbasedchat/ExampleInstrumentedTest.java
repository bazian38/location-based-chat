package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.androidchatsdk.firebaseplugin.firebase.BChatcatNetworkAdapter;
import com.braunster.chatsdk.network.AbstractNetworkAdapter;
import com.braunster.chatsdk.network.BDefines;

import org.jdeferred.DoneCallback;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    static private Context appContext;
    static private BChatcatNetworkAdapter adapter;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        appContext = InstrumentationRegistry.getTargetContext();
        adapter = new BChatcatNetworkAdapter(appContext, true);
    }

    @Test
    public void test() {
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
}
