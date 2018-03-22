package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.chatsdk.Utils.helper.ChatSDKUiHelper;
import com.braunster.chatsdk.network.BNetworkManager;
import com.braunster.chatsdk.network.BPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BPathInstrumentedTest {

    private static BPath path1 = new BPath("moaaaad/aaaa");

    @Test
    public void idForIndexTest() throws Exception
    {
        assertEquals("aaaa",path1.idForIndex(0));
    }

    @Test
    public void getPathTest() throws Exception
    {
        assertEquals("moaaaad/aaaa",path1.getPath());
    }

    @Test
    public void getObjectIdentifierTest() throws Exception
    {
        path1.addPathComponent("testPath1Component","testPath1Uid");
        assertEquals("moaaaad/aaaa/testPath1Component/testPath1Uid", path1.getPath());
    }

}
