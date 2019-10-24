package a3.audientes.hearable;

public class HearableStub implements IHearable {

    private boolean paired = false;


    @Override
    public boolean initToWinIt() {
        return true;
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
}
