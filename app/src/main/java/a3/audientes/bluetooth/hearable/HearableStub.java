package a3.audientes.bluetooth.hearable;

import java.util.ArrayList;
import java.util.List;

import professional.app.TestResult;

public class HearableStub implements IHearable {

    private static final int FAKE_TESTS = 3;
    private static final String NAME = "Anonymous";
    private static final String EMAIL = "Anonymous@FAANG.com";
    private static final String PHONE = "1234567890";
    private static final int AGE = 55;
    private static final int GENDER = 1;
    private boolean paired = false;
    private boolean testRunning = false;

    private List<TestResult> testHistory = new ArrayList<>();

    @Override
    public boolean initToWinIt() {
        for (int i = 0; i < FAKE_TESTS; i++) {
            generateFakeTest();
        }
        return true;
    }

    private TestResult generateFakeTest() {
        TestResult fakeTest = TestResult.createFromMeta(NAME, AGE, GENDER, EMAIL, PHONE);
        testHistory.add(fakeTest);
        return fakeTest;
    }

    @Override
    public boolean pairDevice() {
        paired = true;
        return true;
    }

    @Override
    public boolean isPaired() {
        return paired;
    }

    //TODO: Appears to be an unnecessary method
    @Override
    public boolean setTestProfile(TestResult meta) {
        return true;
    }

    @Override
    public TestResult performHearingTest() {
        return generateFakeTest();
    }

    @Override
    public List<TestResult> getHearingTestHistory() {
        return testHistory;
    }

    @Override
    public boolean startHearingTest() {
        testRunning = true;
        return true;
    }

    @Override
    public boolean isTestRunning() {
        return testRunning;
    }

    @Override
    public boolean interruptHearingTest() {
        testRunning = false;
        return true;
    }

    @Override
    public boolean sendHeardSound() {
        return true;
    }

    @Override
    public boolean sendCannotHearSound() {
        return true;
    }

    @Override
    public TestResult getHearingTestResult() {
        return generateFakeTest();
    }
}
