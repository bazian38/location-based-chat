package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.androidchatsdk.firebaseplugin.firebase.BChatcatNetworkAdapter;
import com.braunster.chatsdk.dao.BMessage;
import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.dao.BUserDao;
import com.braunster.chatsdk.dao.DaoMaster;
import com.braunster.chatsdk.dao.DaoSession;
import com.braunster.chatsdk.dao.UserThreadLink;
import com.braunster.chatsdk.dao.UserThreadLinkDao;
import com.braunster.chatsdk.dao.core.DaoCore;
import com.braunster.chatsdk.dao.entities.BMessageEntity;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BNetworkManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.dao.Property;
import timber.log.Timber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    static private DaoMaster daoMaster;
    static private DaoSession daoSession;
    static private Context appContext;
    static private BThread thread1;
    static private BUser bUser1;
    static private BThread thread2;
    static private BUser bUser2;
    static private UserThreadLink linkData;
    static private boolean isDeleted;

    @Before
    public void initValidData() {
        BNetworkManager.init(InstrumentationRegistry.getTargetContext());
        isDeleted = false;

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

        // Save the entity to the local db.

        linkData = new UserThreadLink();
        linkData.setBThreadDaoId((long) 111111);
        linkData.setBThread(thread1);
        linkData.setBUserDaoId((long) 1111);
        linkData.setBUser(bUser1);
        linkData.setEntityID("1111111111");
        linkData.setId((long) 1111111111);

        DaoCore.createEntity(linkData);

        bUser2 = new BUser();
        bUser2.setEntityID("2222");
        bUser2.setId((long) 2222);

        thread2 = new BThread();

        thread2.setCreator(bUser2);
        thread2.setCreatorEntityId("2222");
        thread2.setType(BThread.Type.Public);
        thread2.setName("testChatBased");
        thread2.setEntityID("222222");
        thread2.setId((long) 222222);
        thread2.setRootKey(BDefines.BRootPath);
        thread2.setApiKey("");

    }

    @After
    public  void deinitData()
    {
        if(!isDeleted)
        {
            DaoCore.deleteEntity(linkData);
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
    public void fetchEntityAndCreateUserThreadLink() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());


        UserThreadLink linkData = DaoCore.fetchEntityWithProperties(UserThreadLink.class,
                new Property[] {UserThreadLinkDao.Properties.BThreadDaoId, UserThreadLinkDao.Properties.BUserDaoId}, thread1.getId(), bUser1.getId());

        assertNotNull(linkData);
        assertEquals(linkData.getBThread().getName(), "testChatBased");
    }

    @Test
    public void removeUserThreadLink() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        DaoCore.deleteEntity(linkData);
        isDeleted = true;
        UserThreadLink linkData = DaoCore.fetchEntityWithProperties(UserThreadLink.class,
                new Property[] {UserThreadLinkDao.Properties.BThreadDaoId, UserThreadLinkDao.Properties.BUserDaoId}, thread1.getId(), bUser1.getId());
        assertNull(linkData);
    }

    @Test
    public void connectUserAndThread(){
//        UserThreadLink linkData = new UserThreadLink();
//        linkData.setBThreadDaoId(thread.getId());
//        linkData.setBThread(thread);
//        linkData.setBUserDaoId(bUser.getId());
//        linkData.setBUser(bUser);
//        DaoCore.createEntity(linkData);
        DaoCore.connectUserAndThread(bUser2,thread2);

        UserThreadLink linkData = DaoCore.fetchEntityWithProperties(UserThreadLink.class,
                new Property[] {UserThreadLinkDao.Properties.BThreadDaoId, UserThreadLinkDao.Properties.BUserDaoId}, thread2.getId(), bUser2.getId());
        assertNotNull(linkData);
    }

    @Test
    public void breakUserAndThread(){
        DaoCore.breakUserAndThread(bUser2,thread2);
        UserThreadLink linkData = DaoCore.fetchEntityWithProperties(UserThreadLink.class,
                new Property[] {UserThreadLinkDao.Properties.BThreadDaoId, UserThreadLinkDao.Properties.BUserDaoId}, thread2.getId(), bUser2.getId());

        assertNull(linkData);
    }
}
