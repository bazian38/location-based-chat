package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.dao.DaoMaster;
import com.braunster.chatsdk.dao.DaoSession;
import com.braunster.chatsdk.dao.core.DaoCore;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BNetworkManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BThreadExampleInstrumentedTest {

    static private DaoMaster daoMaster;
    static private DaoSession daoSession;
    static private Context appContext;
    static private BThread thread;
    static private boolean isDeleted;

    @Before
    public void initValidData() {
        BNetworkManager.init(InstrumentationRegistry.getTargetContext());
        isDeleted = false;

        thread = new BThread();

        thread.setCreator(new BUser());
        thread.setCreatorEntityId("1111");
        thread.setType(BThread.Type.Public);
        thread.setName("testChatBased");
        thread.setEntityID("111111");
        thread.setRootKey(BDefines.BRootPath);
        thread.setApiKey("");

        // Save the entity to the local db.
        DaoCore.createEntity(thread);
    }

    @After
    public  void deinitData()
    {
        if(!isDeleted)
        {
            DaoCore.deleteEntity(thread);
            isDeleted = true;
        }
    }
    @BeforeClass
    public static void setUpBeforeClass()
    {
        DaoCore.init(InstrumentationRegistry.getTargetContext(),"testDataBase");
        daoMaster = DaoCore.daoMaster;
        daoSession = DaoCore.daoSession;


        daoMaster.createAllTables(daoSession.getDatabase(), true);

        appContext = InstrumentationRegistry.getTargetContext();


    }

    @AfterClass
    public static void tearDownAfterClass() {
        daoMaster.dropAllTables(daoSession.getDatabase(),true);
    }

    @Test
    public void fetchEntityAndCreateBThread() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());


        assertNotNull(DaoCore.fetchEntityWithEntityID(BThread.class,"111111"));
        assertEquals(DaoCore.fetchEntityWithEntityID(BThread.class,"111111").getName(), "testChatBased");
    }

    @Test
    public void removeBThread() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        DaoCore.deleteEntity(thread);
        isDeleted = true;
        assertNull(DaoCore.fetchEntityWithEntityID(BThread.class,"111111"));
    }
}
