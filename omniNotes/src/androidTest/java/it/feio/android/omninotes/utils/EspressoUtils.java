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
    private static ViewInteraction backArrowButton = onView(withContentDescription("drawer open"));

    /**
     * this method matches the child of a parentMatcher at a specified position
     *
     * @param parentMatcher is the param used for matching the parent
     * @param position      is the param used for specify the position of the children
     * @return the matcher of the children at the specified position
     */
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

    /**
     * this method clicks on a selected button
     *
     * @param button
     */
    static void clickOnButton(ViewInteraction button) {
        button.perform(click());
    }

    /**
     * this method checks if a viewMatcher matches the View
     *
     * @param view        is the ViewInteraction in which the matcher is applied
     * @param viewMatcher is the matcher
     * @return true or false, depending if the matcher matches the ViewInteraction
     */
    public static boolean checkIfMatches(ViewInteraction view, Matcher<View> viewMatcher) {
        try {
            view.check(matches(viewMatcher));
        } catch (Exception | Error e) {
            return false;
        }
        return true;
    }

    /**
     * this method clicks on a selected UIObject with a specified content description
     *
     * @param contentDescription is the param used for searching the UIObject
     */
    static void clickSelectedUiObjectWithDescription(String contentDescription) {
        try {
            UiObject selectedDescription = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(new UiSelector().descriptionContains(contentDescription));
            selectedDescription.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method write a text in a selected UIObject with a specified text
     *
     * @param textToBeFound    is the text used for finding the right UIObject
     * @param textToBeReplaced is the text used when writing in the selected UIObject
     */
    static void writeSelectedUiObjectWithText(String textToBeFound, String textToBeReplaced) {
        try {
            UiObject selectedDescription = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(new UiSelector().text(textToBeFound));
            selectedDescription.click();
            selectedDescription.setText(textToBeReplaced);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method checks if a text is completely displayed in a view
     *
     * @param p is the id used for searching the text
     * @param s is the text searched
     * @return true or false - depends if the text is or is not completely displayed
     */
    public static boolean checkIfTextIsDisplayed(int p, String s) {
        try {
            onView(allOf(withId(p), withText(s))).check(matches(isCompletelyDisplayed()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * this method is used for going back to the list of notes, after completing a note
     */
    public static void clickBack() {
        onView(isRoot()).perform(waitForViewWithContentDescriptionIsDisplayed("drawer open", 2000));
        clickOnButton(backArrowButton);
    }

    /**
     * this method write text in a selected ViewInteraction
     *
     * @param stringToBeReplaced is the ViewInteraction searched in which the text will be written
     * @param stringReplacer     is the text written
     */
    static void writeText(ViewInteraction stringToBeReplaced, String stringReplacer) {
        stringToBeReplaced.perform(replaceText(stringReplacer));
    }

    /**
     * this method opens each note and clicks on Trash button until the list is empty
     */
    public static void deleteAllNotes() {
        boolean empty = false;
        while (!empty) {
            try {
                openNthNote(0);
                clickOnTrash();
            } catch (Exception e) {
                empty = true;
            }
        }
    }

    /**
     * this method adds the password when the pop-up with the password is displayed
     */
    static void addPasswordWhenRequested() {
        try {
            writeText(onView(withId(R.id.password_request)), "parola");
            clickOnButton(onView(withId(R.id.md_buttonDefaultPositive)));
        } catch (Exception e) {
            Log.e("add password", "Exception when trying to add password");
        }
    }

    /**
     * this ViewAction waits a number of milliseconds for a View with a selected content description to be displayed
     *
     * @param contentDescription is the param used for the selected content description
     * @param millis             is the number of milliseconds
     */
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

    /**
     * this method returns a String from a Matcher
     *
     * @param matcher is the selected Matcher from which we get the text
     * @return the text from the selected Matcher
     */
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
