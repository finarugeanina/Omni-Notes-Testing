package it.feio.android.omninotes.utils;

import androidx.test.espresso.ViewInteraction;

import org.junit.Assert;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfMatches;
import static it.feio.android.omninotes.utils.EspressoUtils.clickBack;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.reminderSetForErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.reminderSetForMessage;
import static org.hamcrest.Matchers.allOf;

class NewCheckList extends NewNote{
    private static ViewInteraction addNewCheckListMatcher = onView(withId(R.id.fab_checklist));
    private static ViewInteraction okButton = onView(allOf(withId(R.id.buttonPositive), withText("OK"), withParent(withId(R.id.button_layout))));

    private static void addCheckListContent(String content1, String content2) {
        EspressoUtils.writeSelectedUiObjectWithText("New item", content1);
        EspressoUtils.writeSelectedUiObjectWithText("New item", content2);
    }

    static void addNewCheckList(String selectedTitle, String content1, String content2, String selectedHour, String selectedMinutes) {
        addNewCheckList(selectedTitle,content1, content2);
        addDateTime(selectedHour, selectedMinutes);
        clickOnButton(okButton);

        Assert.assertTrue(reminderSetForErrorMessage, checkIfMatches(dateTime, withSubstring(reminderSetForMessage)));
        Assert.assertTrue("The message: "+ selectedHour +":" + selectedMinutes + " is not displayed!", checkIfMatches(dateTime, withSubstring(selectedHour + ":" + selectedMinutes)));
        clickBack();
    }

    static void addNewCheckList(String selectedTitle, String content1, String content2) {
        clickOnAddNewNoteButton();
        clickOnButton(addNewCheckListMatcher);
        addTitle(selectedTitle);
        addCheckListContent(content1, content2);
        clickBack();
    }
}
