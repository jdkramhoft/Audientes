package a3.audientes.view.activities;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import a3.audientes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class StartHearingTestTest {

    @Rule
    public ActivityTestRule<StartHearingTest> activityTestRule = new ActivityTestRule<>(StartHearingTest.class);

    private StartHearingTest startHearingTestActivity;

    private Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(HearingTest.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        startHearingTestActivity = activityTestRule.getActivity();
    }

    @Test
    public void launchHearingTestOnButtonClick(){
        assertNotNull(startHearingTestActivity.findViewById(R.id.hearing_button));

        onView(withId(R.id.hearing_button)).perform(click());

        Activity hearingTestActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(hearingTestActivity);
        hearingTestActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        startHearingTestActivity = null;

    }
}