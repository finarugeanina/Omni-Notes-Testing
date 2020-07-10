package it.feio.android.omninotes;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainPageActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkHeaderOptionsFromMainPage() {
        //check if the menu button is displayed and works
        onView(allOf(isAssignableFrom(ImageButton.class),withParent(withId(R.id.toolbar)), isDisplayed())).perform(click());
        onView(withId(R.id.navdrawer_title)).check(matches(withText("Omni Notes Alpha")));
        //close the expanding page and go to the main menu
        ViewInteraction addNote = onView(withId(R.id.fab_expand_menu_button));
        addNote.perform(click());
        //check if the title Notes is present
        onView(allOf(isAssignableFrom(TextView.class),withParent(withId(R.id.toolbar)))).check(matches(withText("Notes")));
        //check if the search button is displayed and if it's working
        ViewInteraction searchButton = onView(withId(R.id.menu_search));
        searchButton.check(matches(isClickable()));
        searchButton.perform(click());
        //check if after user clicks the search button the searching header is displayed
        onView(withId(R.id.search_src_text)).check(matches(withHint("Search in notes")));
        //close the searching header
        onView(withContentDescription("Collapse")).perform(click());
        //check if the sort button is displayed and it's clickable
        ViewInteraction sortNotes = onView(withId(R.id.menu_sort));
        sortNotes.check(matches(isClickable()));
        sortNotes.perform(click());
        onView(allOf(withId(R.id.title),withText("Title"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.title),withText("Title"))).perform(clickXY(-50, 50));
        //check if the more options button is displayed and clickable
        onView(withContentDescription("More options")).check(matches(isDisplayed())).check(matches(isClickable()));
    }
    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }
}
