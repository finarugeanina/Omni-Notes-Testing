package it.feio.android.omninotes;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfMatches;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfTextIsDisplayed;
import static it.feio.android.omninotes.utils.EspressoUtils.deleteAllNotes;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.contentNotDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.emptyListMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.emptyListNotDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.singleItemErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.titleNotDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewTextNote.addNewTextNote;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddSingleTextNoteTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void deleteNotes() {
        deleteAllNotes();
    }

    /**
     * This test is checking the functionality of adding a single text note
     */
    @Test
    public void addSingleTextNoteTest() {
        ViewInteraction list = onView(withId(R.id.list));

        int emptyListId = R.id.empty_list;
        int titleId = R.id.note_title;
        int contentId = R.id.note_content;

        String title = "Meeting with QA Team";
        String content = "Read the new specs";
        String hour = "5";
        String minutes = "30";

        Assert.assertTrue(emptyListNotDisplayedErrorMessage, checkIfTextIsDisplayed(emptyListId, emptyListMessage));
        addNewTextNote(title, content, hour, minutes);

        Assert.assertTrue(singleItemErrorMessage, checkIfMatches(list, hasChildCount(1)));
        Assert.assertTrue(titleNotDisplayedErrorMessage, checkIfTextIsDisplayed(titleId, "Meeting with QA Team"));
        Assert.assertTrue(contentNotDisplayedErrorMessage, checkIfTextIsDisplayed(contentId, "Read the new specs"));
    }
}
