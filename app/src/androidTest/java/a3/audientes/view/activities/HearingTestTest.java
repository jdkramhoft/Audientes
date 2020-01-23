package a3.audientes.view.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import a3.audientes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class HearingTestTest {

    private final int TWO_SECONDS = 2000;
    private final int FORTY_SECONDS = 40000;

    @Rule
    public ActivityTestRule<HearingTest> activityTestRule = new ActivityTestRule<>(HearingTest.class);

    private HearingTest hearingTestActivity;

    @Before
    public void setUp() throws Exception {
        hearingTestActivity = activityTestRule.getActivity();
    }

    @Test
    public void completeHearingTest() throws InterruptedException {
        assertNotNull(hearingTestActivity.findViewById(R.id.hearing_button));

        for (int i = 0; i < hearingTestActivity.getNumOfLevels(); i++) {
            Thread.sleep(TWO_SECONDS);
            onView(withId(R.id.hearing_button)).perform(click());
        }

        AlertDialog dialog = hearingTestActivity.getLatestDialog();
        assertTrue(dialog.isShowing());
    }

    @Test
    public void canHearingTestHandleDeafUser() throws InterruptedException {
        assertNotNull(hearingTestActivity.findViewById(R.id.hearing_button));

        for (int i = 0; i < hearingTestActivity.getNumOfLevels()-1; i++) {
            Thread.sleep(TWO_SECONDS);
            onView(withId(R.id.hearing_button)).perform(click());
        }

        Thread.sleep(FORTY_SECONDS);
        onView(withId(R.id.hearing_button)).perform(click());

        AlertDialog dialog = hearingTestActivity.getLatestDialog();
        assertTrue(dialog.isShowing());

    }

    @After
    public void tearDown() throws Exception {
        hearingTestActivity = null;
    }
}