package com.locationbasedchat.locationbasedchat;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
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
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
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

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.chat_sdk_et_mail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText2.perform(replaceText("ba"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.chat_sdk_et_mail), withText("baz"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText3.perform(replaceText("bazian"));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.chat_sdk_et_mail), withText("bazian"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText4.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.chat_sdk_et_mail), withText("bazian@"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText5.perform(replaceText("bazian@post.bgu.ac.il"));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.chat_sdk_et_mail), withText("bazian@post.bgu.ac.il"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText6.perform(closeSoftKeyboard());

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.chat_sdk_et_mail), withText("bazian@post.bgu.ac.il"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                1),
                        isDisplayed()));
        editText7.perform(pressImeActionButton());

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.chat_sdk_et_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                2),
                        isDisplayed()));
        editText8.perform(replaceText("baZima38"), closeSoftKeyboard());

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.chat_sdk_et_password), withText("baZima38"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.chat_sdk_root_view),
                                        0),
                                2),
                        isDisplayed()));
        editText9.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3511312);
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
            Thread.sleep(3593862);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText10 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText10.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText11 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText11.perform(replaceText("be"), closeSoftKeyboard());

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

        ViewInteraction editText12 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send), withText("be"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText12.perform(replaceText(""));

        ViewInteraction editText13 = onView(
                allOf(withId(R.id.chat_sdk_et_message_to_send),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
                                        1),
                                0),
                        isDisplayed()));
        editText13.perform(closeSoftKeyboard());

        pressBack();

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3572012);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        1),
                        isDisplayed()));
        imageButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        2),
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

        ViewInteraction imageButton3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        3),
                        isDisplayed()));
        imageButton3.perform(click());

        ViewInteraction imageButton4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        4),
                        isDisplayed()));
        imageButton4.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_chat_sdk_logout), withContentDescription("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.Toolbar")),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3568952);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button = onView(
                allOf(withId(R.id.chat_sdk_btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linear_buttons),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.chat_sdk_btn_register), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linear_buttons),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        button2.perform(click());

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
