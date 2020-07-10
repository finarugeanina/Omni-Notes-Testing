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

import it.feio.android.omninotes.utils.NewNoteStringVariables;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfMatches;
import static it.feio.android.omninotes.utils.EspressoUtils.checkIfTextIsDisplayed;
import static it.feio.android.omninotes.utils.EspressoUtils.deleteAllNotes;
import static it.feio.android.omninotes.utils.HomePage.openNthNote;
import static it.feio.android.omninotes.utils.LockSettings.lockNote;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.contentIsDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.lockedIconNotDisplayedMessage;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.titleNotDisplayedErrorMessage;
import static it.feio.android.omninotes.utils.NewTextNote.addNewTextNote;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LockedNoteTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void deleteNotes(){
        deleteAllNotes();
    }

    @Test
    public void lockedNoteTest() {
        int titleId = R.id.note_title;
        int contentId = R.id.note_content;

        ViewInteraction lockedItemView = onView(withId(R.id.lockedIcon));
        ViewInteraction editPageView = onView(withId(R.id.detail_root));

        String password = "parola";
        String question = "Favorite pet?";
        String answer = "Dog";
        String title = "Anniversary";
        String content = "Work Anniversary";

        addNewTextNote(title,content);
        lockNote(password, question, answer);

        Assert.assertTrue(titleNotDisplayedErrorMessage, checkIfTextIsDisplayed(titleId, title));
        Assert.assertFalse(contentIsDisplayedErrorMessage, checkIfTextIsDisplayed(contentId, content));
        Assert.assertTrue(lockedIconNotDisplayedMessage,checkIfMatches(lockedItemView,isDisplayed()));

        openNthNote(0);
        Assert.assertTrue(NewNoteStringVariables.editPageisNotDisplayedMessage,checkIfMatches(editPageView,isDisplayed()));
    }
}
