package it.feio.android.omninotes.utils;

import android.widget.EditText;

import androidx.test.espresso.ViewInteraction;

import org.junit.Assert;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfMatches;
import static it.feio.android.omninotes.utils.EspressoUtils.childAtPosition;
import static it.feio.android.omninotes.utils.EspressoUtils.clickBack;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.reminderSetForErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.reminderSetForMessage;
import static org.hamcrest.Matchers.allOf;

class NewCheckList extends NewNote {
    private static ViewInteraction addNewCheckListMatcher = onView(withId(R.id.fab_checklist));
    private static ViewInteraction okButton = onView(allOf(withId(R.id.buttonPositive), withText("OK"), withParent(withId(R.id.button_layout))));

    /**
     * this method adds 2 contents to a checklist
     *
     * @param content1 is the first content added
     * @param content2 is the second content added
     */
    private static void addCheckListContent(String content1, String content2) {
        EspressoUtils.writeSelectedUiObjectWithText("New item", content1);
        EspressoUtils.writeSelectedUiObjectWithText("New item", content2);
    }

    /**
     * this method adds a new checklist
     *
     * @param selectedTitle   is the title selected to be added
     * @param content1        is the first content added
     * @param content2        is the second content added
     * @param selectedHour    is the number of hours added
     * @param selectedMinutes is the number of minutes added
     */
    static void addNewCheckList(String selectedTitle, String content1, String content2, String selectedHour, String selectedMinutes) {
        addNewCheckList(selectedTitle, content1, content2);
        addDateTime(selectedHour, selectedMinutes);
        clickOnButton(okButton);

        Assert.assertTrue(reminderSetForErrorMessage, checkIfMatches(dateTime, withSubstring(reminderSetForMessage)));
        Assert.assertTrue("The message: " + selectedHour + ":" + selectedMinutes + " is not displayed!", checkIfMatches(dateTime, withSubstring(selectedHour + ":" + selectedMinutes)));
        clickBack();
    }

    /**
     * this method adds a new checklist without selecting a reminder. If the @param position is used, it will check to see if the newly added checklist is added on top of the list
     *
     * @param selectedTitle is the title selected to be added
     * @param content1      is the first content added
     * @param content2      is the second content added
     * @param position      is used to see if the checklist was sorted after Creation date
     */
    static void addNewCheckList(String selectedTitle, String content1, String content2, int... position) {
        clickOnAddNewNoteButton();
        clickOnButton(addNewCheckListMatcher);
        addTitle(selectedTitle);
        addCheckListContent(content1, content2);
        clickBack();

        if (position.length == 1) {
            HomePage.openNthNote(0);
            Assert.assertTrue("The added note is not in the top of the list!", checkIfMatches(onView(withId(R.id.detail_title)), withText(selectedTitle)));
            Assert.assertTrue("The added note is not in the top of the list (first content matching error)!", checkIfMatches(onView(allOf(isAssignableFrom(EditText.class), withParent(childAtPosition(childAtPosition(withId(R.id.detail_content), 0), 0)))), withText(content1)));
            Assert.assertTrue("The added note is not in the top of the list (second content)!", checkIfMatches(onView(allOf(isAssignableFrom(EditText.class), withParent(childAtPosition(childAtPosition(withId(R.id.detail_content), 1), 0)))), withText(content2)));
            clickBack();
        }
    }
}
