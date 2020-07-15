package it.feio.android.omninotes.utils;

import androidx.test.espresso.ViewInteraction;

import java.util.ArrayList;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.EspressoUtils.*;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.titleButton;
import static org.hamcrest.Matchers.allOf;

public class SortNotes {
    public static ArrayList<String> sortNotes(ArrayList<String> arrayWithTitles, int numberOfTimes) {
        int index = 0;
        ViewInteraction sortButton = onView(withId(R.id.menu_sort));

        clickOnButton(sortButton);
        clickOnButton(onView(withText(titleButton)));
        while(index < numberOfTimes) {
            arrayWithTitles.add(getText(allOf(withParent(childAtPosition(childAtPosition(childAtPosition(withId(R.id.list),index),0),1)),withId(R.id.note_title))));
            index++;
        }
        return arrayWithTitles;
    }
}
