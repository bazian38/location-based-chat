package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.fragments.ChatSDKThreadsFragment;
import com.braunster.chatsdk.network.BDefines;
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
public class ThreadsFragmentTest {

    static private Context appContext;
    static private ChatSDKThreadsFragment threadsFragment;
    private static BUser bUser1;
    private BThread thread1;
    private BThread thread2;

    @Before
    public void initValidData() {
        BNetworkManager.init(InstrumentationRegistry.getTargetContext());

        bUser1 = new BUser();
        bUser1.setEntityID("1111");
        bUser1.setId((long) 1111);

        thread1 = new BThread();

        thread1.setCreator(bUser1);
        thread1.setCreatorEntityId("1111");
        thread1.setType(BThread.Type.Public);
        thread1.setName("testChatBased");
        thread1.setEntityID("111111");
        thread1.setId((long) 111111);
        thread1.setRootKey(BDefines.BRootPath);
        thread1.setApiKey("");

        thread2 = new BThread();

        thread2.setCreator(bUser1);
        thread2.setCreatorEntityId("2222");
        thread2.setType(BThread.Type.Public);
        thread2.setName("testChatBased");
        thread2.setEntityID("222222");
        thread2.setId((long) 222222);
        thread2.setRootKey(BDefines.BRootPath);
        thread2.setApiKey("");
    }

    @BeforeClass
    public static void setUpBeforeClass()
    {
        appContext = InstrumentationRegistry.getTargetContext();
        threadsFragment = ChatSDKThreadsFragment.newInstance();
    }

    @AfterClass
    public static void tearDownAfterClass() {

    }

    @Test
    public void sortByComparatorDepartment() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        Map<BThread, Double> threadsDistanceMap = new HashMap<>();

        bUser1.setMetaDepartment("CS");
        thread1.setDepartment("CS");

        threadsDistanceMap.put(thread1, 0.0);
        threadsDistanceMap.put(thread2, 0.0);

        Map<BThread, Double> result = ChatSDKThreadsFragment.sortByComparator(threadsDistanceMap, true,bUser1);

        assertEquals(result.keySet().toArray()[0], thread1);
        assertEquals(result.keySet().toArray()[1], thread2);
    }

    @Test
    public void sortByComparatorDistance() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        Map<BThread, Double> threadsDistanceMap = new HashMap<>();

        threadsDistanceMap.put(thread1, 5.0);
        threadsDistanceMap.put(thread2, 0.0);

        Map<BThread, Double> result = ChatSDKThreadsFragment.sortByComparator(threadsDistanceMap, true,bUser1);

        assertEquals(result.keySet().toArray()[0], thread2);
        assertEquals(result.keySet().toArray()[1], thread1);
    }

    @Test
    public void sortByComparatorDistanceAndDepartment() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        Map<BThread, Double> threadsDistanceMap = new HashMap<>();

        bUser1.setMetaDepartment("CS");
        thread1.setDepartment("CS");

        threadsDistanceMap.put(thread1, 5.0);
        threadsDistanceMap.put(thread2, 0.0);

        Map<BThread, Double> result = ChatSDKThreadsFragment.sortByComparator(threadsDistanceMap, true,bUser1);

        assertEquals(result.keySet().toArray()[0], thread1);
        assertEquals(result.keySet().toArray()[1], thread2);
    }

    @Test
    public void sortByComparatorCourse() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        Map<BThread, Double> threadsDistanceMap = new HashMap<>();

        bUser1.setMetaDepartment("CS");
        bUser1.setMetaCourses("123,456");

        thread1.setCourse("123");

        threadsDistanceMap.put(thread1, 5.0);
        threadsDistanceMap.put(thread2, 5.0);

        Map<BThread, Double> result = ChatSDKThreadsFragment.sortByComparator(threadsDistanceMap, true,bUser1);

        assertEquals(result.keySet().toArray()[0], thread1);
        assertEquals(result.keySet().toArray()[1], thread2);
    }

    @Test
    public void sortByComparatorCourseAndDepartment() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        Map<BThread, Double> threadsDistanceMap = new HashMap<>();

        bUser1.setMetaDepartment("CS");
        bUser1.setMetaCourses("123,456");

        thread1.setCourse("123");
        thread2.setDepartment("CS");

        threadsDistanceMap.put(thread1, 5.0);
        threadsDistanceMap.put(thread2, 5.0);

        Map<BThread, Double> result = ChatSDKThreadsFragment.sortByComparator(threadsDistanceMap, true,bUser1);

        assertEquals(result.keySet().toArray()[0], thread1);
        assertEquals(result.keySet().toArray()[1], thread2);
    }
}
