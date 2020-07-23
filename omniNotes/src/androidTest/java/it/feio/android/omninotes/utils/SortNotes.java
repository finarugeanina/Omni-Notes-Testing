package it.feio.android.omninotes.utils;

import android.widget.ListView;

import androidx.test.espresso.ViewInteraction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfMatches;
import static it.feio.android.omninotes.utils.EspressoUtils.childAtPosition;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.EspressoUtils.getText;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.titleButton;
import static org.hamcrest.Matchers.allOf;

public class SortNotes {
    /**
     * This method is sorting the list of notes and stores the titles in an array and return the array with the titles
     *
     * @param arrayWithTitles - in this parameter are stored the titles after sorting by title
     * @param numberOfTimes   - this is the parameter of how many notes were added
     * @return -  the array with titles
     */
    public static List<String> getArrayWithTitlesAfterSort(List<String> arrayWithTitles, int numberOfTimes) {
        int index = 0;

        sortBy(titleButton);
        while (index < numberOfTimes) {
            arrayWithTitles.add(getText(allOf(withParent(childAtPosition(childAtPosition(childAtPosition(withId(R.id.list), index), 0), 1)), withId(R.id.note_title))));
            index++;
        }
        return arrayWithTitles;
    }

    /**
     * This method sorts the list with notes by a text
     *
     * @param buttonText - the sorting is done by the text selected e.g. Title, Creation date
     */
    public static void sortBy(String buttonText) {
        Map<String, Integer> mapWithSortingOptions = new HashMap<>();
        mapWithSortingOptions.put("Title", 0);
        mapWithSortingOptions.put("Creation date",1);
        mapWithSortingOptions.put("Last modification date",2);
        mapWithSortingOptions.put("Reminder date",3);


        ViewInteraction sortButton = onView(withId(R.id.menu_sort));
        clickOnButton(sortButton);
        do {
            clickOnButton(onView(withText(buttonText)));
            clickOnButton(sortButton);

        } while (!isSelected(mapWithSortingOptions.get(buttonText)));
        clickOnButton(onView(withText(buttonText)));
    }

    /**
     * This method returns true if the right sort by option is checked
     */
    private static boolean isSelected(Integer index) {
        return checkIfMatches(onView(allOf(withId(R.id.radio), withParent(allOf(withId(R.id.content), withParent(childAtPosition(isAssignableFrom(ListView.class), index)))))), isChecked());
    }
}
