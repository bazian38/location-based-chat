package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.fragments.ChatSDKNearbyUsersFragment;
import com.braunster.chatsdk.fragments.ChatSDKThreadsFragment;
import com.braunster.chatsdk.network.BNetworkManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NearbyUsersFragmentTest {

    static private Context appContext;
    private static BUser bUser1;
    private static BUser bUser2;
    private static BUser bUser3;

    @Before
    public void initValidData() {
        BNetworkManager.init(InstrumentationRegistry.getTargetContext());

        bUser1 = new BUser();
        bUser1.setEntityID("1111");
        bUser1.setId((long) 1111);

        bUser2 = new BUser();
        bUser2.setEntityID("2222");
        bUser2.setId((long) 2222);

        bUser3 = new BUser();
        bUser3.setEntityID("2222");
        bUser3.setId((long) 2222);
    }

    @BeforeClass
    public static void setUpBeforeClass()
    {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @AfterClass
    public static void tearDownAfterClass() {

    }

    @Test
    public void sortByComparatorDistance() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        Map<BUser, Double> usersDistanceMap = new HashMap<>();

        usersDistanceMap.put(bUser1, 3.0);
        usersDistanceMap.put(bUser2, 2.3);
        usersDistanceMap.put(bUser3, 2.0);

        Map<BUser, Double> result = ChatSDKNearbyUsersFragment.sortByComparator(usersDistanceMap, true);

        assertEquals(result.keySet().toArray()[0], bUser3);
        assertEquals(result.keySet().toArray()[1], bUser2);
        assertEquals(result.keySet().toArray()[2], bUser1);
    }
}
