package it.feio.android.omninotes.utils;

import androidx.test.espresso.ViewInteraction;

import java.util.Random;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.EspressoUtils.clickSelectedUiObjectWithDescription;
import static it.feio.android.omninotes.utils.EspressoUtils.writeText;
import static it.feio.android.omninotes.utils.NewCheckList.addNewCheckList;
import static it.feio.android.omninotes.utils.NewTextNote.addNewTextNote;
import static org.hamcrest.Matchers.allOf;

public class NewNote {
    private static String titleHintText = "Title";

    static ViewInteraction dateTime = onView(withId(R.id.datetime));
    private static ViewInteraction addNewNote = onView(withId(R.id.fab_expand_menu_button));
    private static ViewInteraction detailTitle = onView(allOf(withId(R.id.detail_title), withHint(titleHintText)));

    /**
     * this method clicks on add new note button
     */
    static void clickOnAddNewNoteButton() {
        clickOnButton(addNewNote);
    }

    /**
     * this method is adding a selected title in the Title field
     *
     * @param selectedTitle is the title added
     */
    static void addTitle(String selectedTitle) {
        writeText(detailTitle, selectedTitle);
    }

    /**
     * this method is adding a date and time
     *
     * @param selectedHour    is the hour selected to be added
     * @param selectedMinutes is the minutes selected to be added
     */
    static void addDateTime(String selectedHour, String selectedMinutes) {
        clickOnButton(dateTime);
        clickSelectedUiObjectWithDescription(selectedHour);
        clickSelectedUiObjectWithDescription(selectedMinutes);
    }

    /**
     * this method adds random text notes or checklists for a selected number of times
     *
     * @param numberOfTimes represents how many notes to add
     */
    public static void addMultipleRandomTextNoteOrChecklist(int numberOfTimes) {
        while (numberOfTimes > 0) {
            Random random = new Random();
            int randomBetweenTextNoteOrCheckList = random.nextInt(2) + 1;
            String randomTitle = generateRandomString();
            String randomContent1 = generateRandomString();
            String randomContent2 = generateRandomString();

            if (randomBetweenTextNoteOrCheckList == 1) {
                addNewTextNote(randomTitle, randomContent1, 1);
            } else {
                addNewCheckList(randomTitle, randomContent1, randomContent2, 1);
            }
            numberOfTimes--;
        }
    }

    /**
     * this method returns a random string with length 10
     *
     * @return a random string
     */
    private static String generateRandomString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int length = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
