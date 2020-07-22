package it.feio.android.omninotes.utils;

import androidx.test.espresso.ViewInteraction;

import org.junit.Assert;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfMatches;
import static it.feio.android.omninotes.utils.EspressoUtils.clickBack;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.EspressoUtils.writeText;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.reminderSetForErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.reminderSetForMessage;
import static org.hamcrest.Matchers.allOf;

public class NewTextNote extends NewNote {
    private static ViewInteraction addNewTextNoteMatcher = onView(withId(R.id.fab_note));
    private static ViewInteraction okButton = onView(allOf(withId(R.id.buttonPositive), withText("OK"), withParent(withId(R.id.button_layout))));
    private static ViewInteraction detailContent = onView(allOf(withId(R.id.detail_content), withHint("Content")));

    /**
     * this method adds content to the note
     *
     * @param selectedContent is the text to be added in the content field
     */
    private static void addContent(String selectedContent) {
        writeText(detailContent, selectedContent);
    }

    /**
     * this method adds a new text note
     *
     * @param selectedTitle   is the title selected to be added
     * @param selectedContent is the content added
     * @param selectedHour    is the number of hours added
     * @param selectedMinutes is the number of minutes added
     */
    public static void addNewTextNote(String selectedTitle, String selectedContent, String selectedHour, String selectedMinutes) {
        addNewTextNote(selectedTitle, selectedContent);
        addDateTime(selectedHour, selectedMinutes);
        clickOnButton(okButton);
        Assert.assertTrue(reminderSetForErrorMessage, checkIfMatches(dateTime, withSubstring(reminderSetForMessage)));
        Assert.assertTrue("The message: " + selectedHour + ":" + selectedMinutes + " is not displayed!", checkIfMatches(dateTime, withSubstring(selectedHour + ":" + selectedMinutes)));
        clickBack();
    }

    /**
     * this method adds a new text note without selecting a reminder. If the @param position is used, it will check to see if the newly added text note is added on top of the list
     *
     * @param selectedTitle   is the title selected to be added
     * @param selectedContent is the content added
     * @param position        is used to see if the checklist was sorted after Creation date
     */
    public static void addNewTextNote(String selectedTitle, String selectedContent, int... position) {
        clickOnAddNewNoteButton();
        clickOnButton(addNewTextNoteMatcher);
        addTitle(selectedTitle);
        addContent(selectedContent);
        clickBack();

        if (position.length > 0) {
            HomePage.openNthNote(0);
            Assert.assertTrue("The added note is not in top of the list!(titles are not matching)", checkIfMatches(onView(withId(R.id.detail_title)), withText(selectedTitle)));
            Assert.assertTrue("The added note is not in top of the list!(content is not matching)", checkIfMatches(onView(withId(R.id.detail_content)), withText(selectedContent)));
            clickBack();
        }
    }
}
