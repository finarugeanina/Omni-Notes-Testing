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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfMatches;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfTextIsDisplayed;
import static it.feio.android.omninotes.utils.EspressoUtils.clickBack;
import static it.feio.android.omninotes.utils.EspressoUtils.deleteAllNotes;
import static it.feio.android.omninotes.utils.HomePage.openNthNote;
import static it.feio.android.omninotes.utils.MainMenu.openMainMenu;
import static it.feio.android.omninotes.utils.MainMenu.viewArchivedNotes;
import static it.feio.android.omninotes.utils.MoreOptions.clickOnArchive;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.archivedIconNotDisplayedMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.contentNotDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.emptyListMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.emptyListNotDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.titleNotDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewTextNote.addNewTextNote;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ArchivedTextNoteTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void deleteNotes(){
        deleteAllNotes();
    }

    @Test
    public void archivedTextNoteTest() {
        String title = "Meeting with QA Team";
        String content = "Read the new specs";

        int emptyListId = R.id.empty_list;
        int titleId = R.id.note_title;
        int contentId = R.id.note_content;

        ViewInteraction archivedItemView = onView(withId(R.id.archivedIcon));

        addNewTextNote(title,content);
        clickBack();

        openNthNote(0);
        clickOnArchive();
        Assert.assertTrue(emptyListNotDisplayedErrorMessage, checkIfTextIsDisplayed(emptyListId, emptyListMessage));

        openMainMenu();
        viewArchivedNotes();

        Assert.assertTrue(titleNotDisplayedErrorMessage, checkIfTextIsDisplayed(titleId, title));
        Assert.assertTrue(contentNotDisplayedErrorMessage, checkIfTextIsDisplayed(contentId, content));
        Assert.assertTrue(archivedIconNotDisplayedMessage,checkIfMatches(archivedItemView,isDisplayed()));
    }
}
