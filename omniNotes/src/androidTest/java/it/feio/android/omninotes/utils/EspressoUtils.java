package it.feio.android.omninotes.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.HomePage.openNthNote;
import static it.feio.android.omninotes.utils.MoreOptions.clickOnTrash;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

public class EspressoUtils {
    private static ViewInteraction backArrowButton =  onView(withContentDescription("drawer open"));

    static Matcher<View> childAtPosition(Matcher<View> parentMatcher, int position) {
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

    static void clickOnButton(ViewInteraction button) {
        button.perform(click());
    }

    public static boolean checkIfMatches(ViewInteraction view, Matcher<View> viewMatcher) {
        try {
            view.check(matches(viewMatcher));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    static void clickSelectedUiObjectWithDescription(String contentDescription){
        try {
            UiObject selectedDescription = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(new UiSelector().descriptionContains(contentDescription));
            selectedDescription.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void writeSelectedUiObjectWithText(String textToBeFound, String textToBeReplaced){
        try {
            UiObject selectedDescription = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(new UiSelector().text(textToBeFound));
            selectedDescription.click();
            selectedDescription.setText(textToBeReplaced);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getTextFromSelectedUiObject(int index) throws UiObjectNotFoundException {

            UiObject selectedDescription = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(new UiSelector().index(index));

        return selectedDescription.getText();
    }

    public static boolean checkIfTextIsDisplayed(int p, String s) {
        try{
            onView(allOf(withId(p), withText(s))).check(matches(isCompletelyDisplayed()));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static void clickBack() {
        onView(isRoot()).perform(waitForViewWithContentDescriptionIsDisplayed("drawer open", 2000));
        clickOnButton(backArrowButton);
    }

    static void writeText(ViewInteraction stringToBeReplaced, String stringReplacer) {
        stringToBeReplaced.perform(replaceText(stringReplacer));
    }

    public static void deleteAllNotes(){

        boolean empty = false;
        while(!empty) {
            try {
                openNthNote(0);
                clickOnTrash();
            } catch (Exception e) {
                empty = true;
            }
        }

    }

    static void addPasswordWhenRequested() {
        try{
            writeText(onView(withId(R.id.password_request)),"parola");
            clickOnButton(onView(withId(R.id.md_buttonDefaultPositive)));
        }catch (Exception e){
            Log.e("add password", "Exception when trying to add password");
        }
    }

    private static ViewAction waitForViewWithContentDescriptionIsDisplayed(final String contentDescription, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with content description <" + contentDescription + "> has been displayed during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> matchId = withContentDescription(contentDescription);
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchId.matches(child) && matchDisplayed.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);
            }
        };
    }

    static String getText(final Matcher<View> matcher) {
        try {
            final String[] stringHolder = {null};
            onView(matcher).perform(new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return isAssignableFrom(TextView.class);
                }

                @Override
                public String getDescription() {
                    return "get text";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    TextView tv = (TextView) view;
                    stringHolder[0] = tv.getText().toString();
                }
            });
            if (stringHolder[0] == null || stringHolder[0] == "") {
                fail("no text found");
            }
            return stringHolder[0];
        } catch (Exception e) {
            fail("null found");
            return null;
        }
    }
}
