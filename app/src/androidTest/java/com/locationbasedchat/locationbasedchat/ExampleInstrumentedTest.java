package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.androidchatsdk.firebaseplugin.firebase.BChatcatNetworkAdapter;
import com.braunster.chatsdk.dao.BMessage;
import com.braunster.chatsdk.dao.BUserDao;
import com.braunster.chatsdk.dao.DaoMaster;
import com.braunster.chatsdk.dao.DaoSession;
import com.braunster.chatsdk.dao.core.DaoCore;
import com.braunster.chatsdk.dao.entities.BMessageEntity;
import com.braunster.chatsdk.network.AbstractNetworkAdapter;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BNetworkManager;

import org.jdeferred.DoneCallback;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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

    private String testEmail;
    private String testPassword;
    static private DaoMaster daoMaster;
    static private DaoSession daoSession;
    static private BUserDao bUserDao;
    static private Context appContext;
    static private BChatcatNetworkAdapter adapter;

//    @Rule
//    public ActivityTestRule<ChatSDKLoginActivity> mActivityRule = new ActivityTestRule<>(sdkUiHelper.loginActivity);

    @Before
    public void initValidString() {
        testEmail = "agbaria@post.bgu.ac.il";
        testPassword = "123456";
        BNetworkManager.init(InstrumentationRegistry.getTargetContext());

    }

    @BeforeClass
    public static void setUpBeforeClass()
    {
        DaoCore.init(InstrumentationRegistry.getTargetContext(),"testDataBase");
        daoMaster = DaoCore.daoMaster;
        daoSession = DaoCore.daoSession;
        bUserDao = daoSession.getBUserDao();

        daoMaster.createAllTables(daoSession.getDatabase(), true);

        appContext = InstrumentationRegistry.getTargetContext();
        adapter = new BChatcatNetworkAdapter(appContext);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        daoMaster.dropAllTables(daoSession.getDatabase(),true);
    }

    @Test
    public void useAppContext() throws Exception {
//        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        // Creating the new message.
        BMessage message = new BMessage();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        message.setDate(df.parse(formattedDate));
        message.setType(BMessageEntity.Type.TEXT);
        message.setText("aaa");
        message.setEntityID("1111");
        message.setIsRead(false);

        // If we dont have the sender and thread we wont open the
        // chat activity when the notification is pressed.
        // Else we are setting the thread and sender to the message.
        // Marking the thread as not deleted.

        message.setBUserSender(null);
        message.setThread(null);
        message = DaoCore.createEntity(message);

        assertNotNull(DaoCore.fetchEntityWithEntityID(BMessage.class,"1111"));
        assertEquals(DaoCore.fetchEntityWithEntityID(BMessage.class,"1111").getText(), "aaa");
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
}
