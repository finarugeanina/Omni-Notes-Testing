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
import java.util.stream.Collectors;

import it.feio.android.omninotes.utils.SortNotes;

import static it.feio.android.omninotes.utils.EspressoUtils.deleteAllNotes;
import static it.feio.android.omninotes.utils.NewNote.addMultipleRandomTextNoteOrChecklist;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SortNotesTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void deleteNotes(){
        deleteAllNotes();
    }

    @Test
    public void sortNotesTest() {
        int numberOfTimes = 5;
        ArrayList<String> arrayWithTitles = new ArrayList<>();

        addMultipleRandomTextNoteOrChecklist(numberOfTimes);

        arrayWithTitles = new ArrayList<>(SortNotes.sortNotes(arrayWithTitles, numberOfTimes));
        Assert.assertEquals("The list is not sorted!", arrayWithTitles.stream().sorted().collect(Collectors.toList()), arrayWithTitles);
    }
}
