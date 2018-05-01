package com.locationbasedchat.locationbasedchat;



import android.os.Build;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
   // @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);


    @Test
    public void mainActivityTest() {

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText = onView(
                allOf(withId(R.id.chat_sdk_et_mail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.chat_sdk_et_mail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText2.perform(pressImeActionButton());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.chat_sdk_et_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                2),
                        isDisplayed()));
        editText3.perform(pressImeActionButton());

        ViewInteraction button = onView(
                allOf(withId(R.id.chat_sdk_btn_register), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linear_buttons),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.chat_sdk_btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linear_buttons),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.chat_sdk_et_mail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText4.perform(replaceText("bazian@post.bgu.ac.il"), closeSoftKeyboard());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.chat_sdk_et_mail), withText("bazian@post.bgu.ac.il"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText5.perform(pressImeActionButton());

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.chat_sdk_et_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                2),
                        isDisplayed()));
        editText6.perform(replaceText("baZima38"), closeSoftKeyboard());

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.chat_sdk_et_password), withText("baZima38"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                2),
                        isDisplayed()));

//        if (Build.VERSION.SDK_INT >= 23) {
//            UiDevice device = UiDevice.getInstance(getInstrumentation());
//            UiObject allowPermissions = device.findObject(new UiSelector().text("ALLOW"));
//            if (allowPermissions.exists()) {
//                try {
//                    allowPermissions.click();
//                } catch (UiObjectNotFoundException e) {
//                    Timber.e(e, "There is no permissions dialog to interact with ");
//                }
//            }
//        }

        editText7.perform(pressImeActionButton());

//        if (Build.VERSION.SDK_INT >= 23) {
//            UiDevice device = UiDevice.getInstance(getInstrumentation());
//            UiObject allowPermissions = device.findObject(new UiSelector().text("ALLOW"));
//            if (allowPermissions.exists()) {
//                try {
//                    allowPermissions.click();
//                } catch (UiObjectNotFoundException e) {
//                    Timber.e(e, "There is no permissions dialog to interact with ");
//                }
//            }
//        }

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.list_threads),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(0);
        linearLayout.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText8.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText9.perform(replaceText("hi"), closeSoftKeyboard());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.chat_sdk_btn_options), withContentDescription("Message option button"),
                        childAtPosition(
                                allOf(withId(R.id.linear),
                                        childAtPosition(
                                                withId(R.id.chat_sdk_message_box),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.chat_sdk_btn_chat_send_message), withText("Send"),
                        childAtPosition(
                                allOf(withId(R.id.linear),
                                        childAtPosition(
                                                withId(R.id.chat_sdk_message_box),
                                                0)),
                                2),
                        isDisplayed()));
        textView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText10 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send), withText("hi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText10.perform(replaceText(""));

        ViewInteraction editText11 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText11.perform(closeSoftKeyboard());

        pressBack();

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_chat_sdk_thread_details), withContentDescription("Thread Details"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.Toolbar")),
                                        0),
                                1),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3598645);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        1),
                        isDisplayed()));
        imageButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.list_threads),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(0);
        linearLayout2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText12 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText12.perform(click());

        ViewInteraction editText13 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText13.perform(replaceText("hi"), closeSoftKeyboard());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.chat_sdk_btn_chat_send_message), withText("Send"),
                        childAtPosition(
                                allOf(withId(R.id.linear),
                                        childAtPosition(
                                                withId(R.id.chat_sdk_message_box),
                                                0)),
                                2),
                        isDisplayed()));
        textView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText14 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send), withText("hi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText14.perform(replaceText(""));

        ViewInteraction editText15 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText15.perform(closeSoftKeyboard());

        pressBack();

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        2),
                        isDisplayed()));
        imageButton3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        3),
                        isDisplayed()));
        imageButton4.perform(click());

        ViewInteraction imageButton5 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        4),
                        isDisplayed()));
        imageButton5.perform(click());

        ViewInteraction editText16 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazian"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText16.perform(click());

        ViewInteraction editText17 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazian"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText17.perform(replaceText("Ahmad Bazia"));

        ViewInteraction editText18 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazia"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText18.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText19 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazia"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText19.perform(replaceText("Ahmad Bazi"));

        ViewInteraction editText20 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText20.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText21 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText21.perform(replaceText("Ahmad Bazin"));

        ViewInteraction editText22 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazin"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText22.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText23 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazin"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText23.perform(pressImeActionButton());

        ViewInteraction editText24 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazin"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText24.perform(click());

        ViewInteraction editText25 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazin"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText25.perform(replaceText("Ahmad Bazi"));

        ViewInteraction editText26 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText26.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText27 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText27.perform(replaceText("Ahmad Bazia"));

        ViewInteraction editText28 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazia"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText28.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText29 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazia"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText29.perform(replaceText("Ahmad Bazian"));

        ViewInteraction editText30 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazian"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText30.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText31 = onView(
                allOf(withId(R.id.chat_sdk_et_name), withText("Ahmad Bazian"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        0),
                                1),
                        isDisplayed()));
        editText31.perform(pressImeActionButton());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_chat_sdk_logout), withContentDescription("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.Toolbar")),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
