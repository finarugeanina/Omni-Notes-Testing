package it.feio.android.omninotes.utils;

import android.util.Log;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import it.feio.android.omninotes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static it.feio.android.omninotes.utils.EspressoUtils.clickBack;
import static it.feio.android.omninotes.utils.EspressoUtils.writeText;
import static it.feio.android.omninotes.utils.MoreOptions.clickOnLock;
import static org.hamcrest.Matchers.allOf;

public class LockSettings {
    public static void lockNote(String password, String question, String answer) {
        clickOnLock();
        AddDetailsForLock(password, question, answer);
        clickBack();
    }

    private static void AddDetailsForLock(String password, String question, String answer) {
        ViewInteraction initialPasswordView = onView(withId(R.id.password));
        ViewInteraction confirmPasswordView = onView(withId(R.id.password_check));
        ViewInteraction questionView = onView(withId(R.id.question));
        ViewInteraction answerView = onView(withId(R.id.answer));
        ViewInteraction confirmAnswerView = onView(withId(R.id.answer_check));
        ViewInteraction confirmPasswordRequest = onView(withId(R.id.password_confirm));

        try{
            writeText(initialPasswordView,password);
            writeText(confirmPasswordView,password);

            writeText(questionView, question);
            writeText(answerView, answer);

            writeText(confirmAnswerView,answer);
            closeSoftKeyboard();

            confirmPasswordRequest.check(matches(allOf( isEnabled(), isClickable()))).perform(
                    new ViewAction() {
                        @Override
                        public Matcher<View> getConstraints() {
                            return ViewMatchers.isEnabled();
                        }

                        @Override
                        public String getDescription() {
                            return "click ok button";
                        }

                        @Override
                        public void perform(UiController uiController, View view) {
                            view.performClick();
                        }
                    }
            );
        }catch(Exception e){
            Log.e("complete the details","There is no need to complete the details for locking the note");
        }
    }


}
