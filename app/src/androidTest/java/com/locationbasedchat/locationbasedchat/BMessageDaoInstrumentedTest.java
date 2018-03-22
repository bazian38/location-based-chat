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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BMessageDaoInstrumentedTest {

    static private DaoMaster daoMaster;
    static private DaoSession daoSession;
    static private Context appContext;
    static private BMessage message;

//    @Rule
//    public ActivityTestRule<ChatSDKLoginActivity> mActivityRule = new ActivityTestRule<>(sdkUiHelper.loginActivity);

    @Before
    public void initValidString() {
        BNetworkManager.init(InstrumentationRegistry.getTargetContext());

        // Creating the new message.
        message = new BMessage();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        try {
            message.setDate(df.parse(formattedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    }

    @After
    public  void deinitMessage()
    {
        DaoCore.deleteEntity(message);
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
    public void fetchEntityAndCreateBMessage() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());


        assertNotNull(DaoCore.fetchEntityWithEntityID(BMessage.class,"1111"));
        assertEquals(DaoCore.fetchEntityWithEntityID(BMessage.class,"1111").getText(), "aaa");
    }

    @Test
    public void removeBMessage() throws Exception {
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());

        DaoCore.deleteEntity(message);
        assertNull(DaoCore.fetchEntityWithEntityID(BMessage.class,"1111"));
    }
}
