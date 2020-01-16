package a3.audientes.dto;

public class Sound {

    private int duration;
    private int sampleRate;
    private double freqOfTone;
    private int numSamples;
    private double[] sample;
    private byte[] generatedSnd;

    public Sound(double freqOfTone, int duration, int sampleRate) {
        this.freqOfTone = freqOfTone;
        this.duration = duration;
        this.sampleRate = sampleRate;
        this.numSamples = this.duration * this.sampleRate;
        this.sample = new double[this.numSamples];
        this.generatedSnd = new byte[2 * this.numSamples];
        genTone();
    }

    /**
     * The method genTone() used to generate a sound ad a given freq is copied from this link
     * https://stackoverflow.com/questions/8698633/how-to-generate-a-particular-sound-frequency/8698670
     */
    private void genTone(){
        // fill out the array
        for (int i = 0; i < this.numSamples; ++i) {
            this.sample[i] = Math.sin(2 * Math.PI * i / (this.sampleRate/this.freqOfTone));
        }
        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : this.sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            this.generatedSnd[idx++] = (byte) (val & 0x00ff);
            this.generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    // Getters and setters

    public int getSampleRate() {
        return sampleRate;
    }

    public byte[] getGeneratedSnd() {
        return generatedSnd;
    }

    public int getFreqOfTone() {
        return (int)freqOfTone;
    }
}
