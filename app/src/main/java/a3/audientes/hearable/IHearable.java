package a3.audientes.hearable;

import java.util.List;

import professional.app.TestResult;

public interface IHearable {

    /**
     * Initialize possibly necessary variables.
     * Call until true returned once before using icon_hearable.
     * @return true on success
     */
    public boolean initToWinIt();


    /**
     * Pairs phone with nearby audientes icon_hearable
     * Assumes necessary permissions have been granted to app.
     * Current simulation requires location permission.
     * @return true on success
     */
    public boolean pairDevice();


    /**
     * Something like this is necessary as bluetooth connections can time out or disconnect.
     * Hearable sim times out every so often.
     * Not entirely sure how to implement/how the reconnecting that happens works.
     * @return true if paired with audientes icon_hearable
     */
    public boolean isPaired();


    /**
     * Might be necessary to run a hearing test
     * @param meta - TestResult object containing profile information
     * @return true on success
     */
    public boolean setTestProfile(TestResult meta);

    /**
     * Not sure whether this is feasible.
     * Not sure how to allow interrupts.
     * All-in-one method for letting audientes handle it
     *
     * @return result as TestResult object instance
     */
    public TestResult performHearingTest();


    /**
     * Retrieves locally stored hearing test results.
     * // Not guaranteed to be in proper chronological order
     * @return List of results as TestResult object instances
     */
    public List<TestResult> getHearingTestHistory();




    /*
     *  START OF ALTERNATIVE TEST RESULT METHODS
     *      HARD TO SEE HOW THESE WOULD WORK
     */

    public boolean startHearingTest();
    public boolean isTestRunning();
    public boolean interruptHearingTest();
    public boolean sendHeardSound();
    public boolean sendCannotHearSound();
    public TestResult getHearingTestResult();

    /*
     *  END OF ALTERNATIVE TEST RESULT METHODS
     */







}
