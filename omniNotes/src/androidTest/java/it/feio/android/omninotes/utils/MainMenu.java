package it.feio.android.omninotes.utils;

import android.widget.ImageButton;

import androidx.test.espresso.ViewInteraction;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class MainMenu {
    /**
     * this method opens the Main Menu
     */
    public static void openMainMenu() {
        ViewInteraction mainMenuButton = onView(allOf(isAssignableFrom(ImageButton.class), withParent(withId(R.id.toolbar))));
        mainMenuButton.perform(click());
    }

    /**
     * this method opens the Archived notes list
     */
    public static void viewArchivedNotes() {
        ViewInteraction archivedNotes = onView(withText("Archive"));
        archivedNotes.perform(click());
    }
}
