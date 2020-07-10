package it.feio.android.omninotes.utils;

import androidx.test.espresso.ViewInteraction;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static it.feio.android.omninotes.utils.EspressoUtils.addPasswordWhenRequested;
import static it.feio.android.omninotes.utils.EspressoUtils.childAtPosition;
import static it.feio.android.omninotes.utils.EspressoUtils.clickOnButton;
import static org.hamcrest.Matchers.allOf;

public class HomePage {
    public static void openNthNote(int position) {
        ViewInteraction nthNote = onView(allOf(withId(R.id.root), childAtPosition(withId(R.id.list),position)));
        clickOnButton(nthNote);
        addPasswordWhenRequested();
    }
}
