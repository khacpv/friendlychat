/**
 * Copyright Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.firebase.codelab.friendlychat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void reset(){
        clearSharedPrefs(mActivityRule.getActivity());
    }

    // Add instrumentation test here
    @Test
    public void verifySendChat() {
        onView(ViewMatchers.withId(R.id.messageEditText)).perform(clearText(), typeText("sample " +
                "message"));
        onView(ViewMatchers.withId(R.id.sendButton)).perform(click());
        onView(ViewMatchers.withId(R.id.messageEditText)).check(matches(withText("")));
    }

    @Test
    public void verifyMaxLengthChat() {
        onView(ViewMatchers.withId(R.id.messageEditText)).perform(clearText(), typeText("this is " +
                "a very looooooooong text"));
        onView(ViewMatchers.withId(R.id.messageEditText)).check(matches(withText("this is a very " +
                "loooo")));
    }

    private void clearSharedPrefs(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(CodelabPreferences.FRIENDLY_MSG_LENGTH, Context
                        .MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
