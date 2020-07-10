package it.feio.android.omninotes.utils;

import androidx.test.espresso.ViewInteraction;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.EspressoUtils.writeText;
import static org.hamcrest.Matchers.allOf;

public class NewTextNote extends NewNote{
    private static ViewInteraction addNewTextNoteMatcher = onView(withId(R.id.fab_note));
    private static ViewInteraction okButton = onView(allOf(withId(R.id.buttonPositive), withText("OK"), withParent(withId(R.id.button_layout))));
    private static ViewInteraction detailContent = onView(allOf(withId(R.id.detail_content),withHint("Content")));

    private static void addContent(String selectedContent) {
        writeText(detailContent, selectedContent );
    }

    public static void addNewTextNote(String selectedTitle, String selectedContent, String selectedHour, String selectedMinutes) {
        addNewTextNote(selectedTitle,selectedContent);
        addDateTime(selectedHour, selectedMinutes);

        clickOnButton(okButton);
    }

    public static void addNewTextNote(String selectedTitle, String selectedContent) {
        clickOnAddNewNoteButton();
        clickOnButton(addNewTextNoteMatcher);
        addTitle(selectedTitle);
        addContent(selectedContent);
    }
}
