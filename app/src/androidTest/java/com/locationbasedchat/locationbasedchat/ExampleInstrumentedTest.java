package com.locationbasedchat.locationbasedchat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.braunster.chatsdk.Utils.helper.ChatSDKUiHelper;
import com.braunster.chatsdk.activities.ChatSDKLoginActivity;
import com.braunster.chatsdk.activities.ChatSDKMainActivity;
import com.braunster.chatsdk.network.BNetworkManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
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

    private static ChatSDKUiHelper sdkUiHelper = ChatSDKUiHelper.initDefault();
    private static BNetworkManager networkManager = BNetworkManager.init();

    @Rule
    public ActivityTestRule<ChatSDKLoginActivity> mActivityRule = new ActivityTestRule<>(sdkUiHelper.loginActivity);

    @Before
    public void initValidString() {
        testEmail = "agbaria@post.bgu.ac.il";
        testPassword = "123456";
    }

    @Test
    public void loginTest() throws Exception {
        /*// Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.locationbasedchat.locationbasedchat", appContext.getPackageName());*/



        onView(withId(R.id.chat_sdk_et_mail)).perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.chat_sdk_et_password)).perform(typeText(testPassword), closeSoftKeyboard());
        onView(withId(R.id.chat_sdk_btn_login)).perform(click());

        intended(allOf(
                hasComponent(hasShortClassName(".ChatSDKMainActivity")),
                toPackage("com.braunster.chatsdk.activities"),
                hasAction(ChatSDKMainActivity.Action_clear_data)));
    }
}
