package it.feio.android.omninotes.utils;

import androidx.test.espresso.ViewInteraction;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.EspressoUtils.clickSelectedUiObjectWithDescription;
import static it.feio.android.omninotes.utils.EspressoUtils.writeText;
import static org.hamcrest.Matchers.allOf;

public class NewNote {
    private static String titleHintText = "Title";

    public static ViewInteraction dateTime = onView(withId(R.id.datetime));
    private static ViewInteraction addNewNote = onView(withId(R.id.fab_expand_menu_button));
    private static ViewInteraction detailTitle = onView(allOf(withId(R.id.detail_title),withHint(titleHintText)));

    static void clickOnAddNewNoteButton() {
        clickOnButton(addNewNote);
    }

    static void addTitle(String selectedTitle) {
        writeText(detailTitle, selectedTitle);
    }

    static void addDateTime(String selectedHour, String selectedMinutes) {
        clickOnButton(dateTime);
        clickSelectedUiObjectWithDescription(selectedHour);
        clickSelectedUiObjectWithDescription(selectedMinutes);
    }


}
