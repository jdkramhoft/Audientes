package a3.audientes.model;

import java.util.ArrayList;
import java.util.List;

public class AudiogramManager {

    private static AudiogramManager single_instance = null;
    private List<Audiogram> audiograms = new ArrayList<>();
    private Audiogram currentAudiogram = new Audiogram();

    private AudiogramManager() {
        Audiogram newAudiogram = new Audiogram();
        newAudiogram.addIndex(new int[]{500,5});
        newAudiogram.addIndex(new int[]{1000,7});
        newAudiogram.addIndex(new int[]{2000,4});
        newAudiogram.addIndex(new int[]{5000,5});
        newAudiogram.addIndex(new int[]{10000,7});
        audiograms.add(newAudiogram);
    }

    public static AudiogramManager getInstance() {
        if (single_instance == null)
            single_instance = new AudiogramManager();

        return single_instance;
    }

    public void addAudiogram(Audiogram audiogram){
        audiograms.add(audiogram);
    }

    public List<Audiogram> getAudiograms(){
        return audiograms;
    }

    public void resetAudiogram(){
        currentAudiogram = new Audiogram();
    }

    public void addIndexToCurrentAudiogram(int[] xy){
        currentAudiogram.addIndex(xy);
    }

    public void saveCurrentAudiogram(){
        audiograms.add(currentAudiogram);
    }
}
