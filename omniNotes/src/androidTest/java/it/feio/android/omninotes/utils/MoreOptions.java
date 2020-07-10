package it.feio.android.omninotes.utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static it.feio.android.omninotes.utils.EspressoUtils.addPasswordWhenRequested;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.archiveButton;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.lockButton;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.moreOptionsButton;
import static it.feio.android.omninotes.utils.NewNoteStringVariables.trashButton;

public class MoreOptions {
    private static void openMoreOptionsMenu(){
        clickOnButton(onView(withContentDescription(moreOptionsButton)));
    }

    static void clickOnTrash(){
        openMoreOptionsMenu();
        clickOnButton(onView(withText(trashButton)));
    }

    static void clickOnLock(){
        openMoreOptionsMenu();
        clickOnButton(onView(withText(lockButton)));
        addPasswordWhenRequested();
    }

    public static void clickOnArchive(){
        openMoreOptionsMenu();
        clickOnButton(onView(withText(archiveButton)));
    }
}
