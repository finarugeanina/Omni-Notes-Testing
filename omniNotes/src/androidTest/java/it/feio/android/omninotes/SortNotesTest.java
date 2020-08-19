package it.feio.android.omninotes;


import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.feio.android.omninotes.utils.EspressoUtils.deleteAllNotes;
import static it.feio.android.omninotes.utils.NewNote.addMultipleRandomTextNoteOrChecklist;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.creationDateButton;
import static it.feio.android.omninotes.utils.SortNotes.getArrayWithTitlesAfterSort;
import static it.feio.android.omninotes.utils.SortNotes.sortBy;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SortNotesTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void deleteNotes() {
        deleteAllNotes();
    }

    /**
     * This test is checking the Sort by title functionality after it adds multiple random notes.
     */
    @Test
    public void sortNotesTest() {
        int numberOfTimes = 3;
        List<String> arrayWithTitles = new ArrayList<>();

        sortBy(creationDateButton);
        addMultipleRandomTextNoteOrChecklist(numberOfTimes);

        arrayWithTitles = new ArrayList<>(getArrayWithTitlesAfterSort(arrayWithTitles, numberOfTimes));
        Assert.assertEquals("The list is not sorted!", arrayWithTitles.stream().sorted().collect(Collectors.toList()), arrayWithTitles);
    }
}
