package a3.audientes.view.activities;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import static org.junit.Assert.*;

public class SplashScreenTest {

    @Test
    public void shouldSwitchActivity() throws InterruptedException {
        ActivityScenario scenario = ActivityScenario.launch(SplashScreen.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        assertNotEquals(scenario.getState(), Lifecycle.State.DESTROYED);
        Thread.sleep(SplashScreen.SPLASH_TIME + 500);
        assertEquals(scenario.getState(), Lifecycle.State.DESTROYED);
    }

}