package a3.audientes.model;

import java.util.ArrayList;
import java.util.List;

public class AudiogramManager {

    private static AudiogramManager single_instance = null;
    private List<Audiogram> audiograms = new ArrayList<>();
    private Audiogram currentAudiogram;

    private AudiogramManager() {
        currentAudiogram = new Audiogram();
        currentAudiogram.addIndex(new int[]{500,5});
        currentAudiogram.addIndex(new int[]{1000,7});
        currentAudiogram.addIndex(new int[]{2000,4});
        currentAudiogram.addIndex(new int[]{5000,5});
        currentAudiogram.addIndex(new int[]{10000,7});
        audiograms.add(currentAudiogram);
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

    public void setAudiograms(List<Audiogram> audiograms) {
        this.audiograms = audiograms;
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

    public int getNextId(){
        if(audiograms.size() == 0){
            return 0;
        }else{
            return audiograms.get(audiograms.size()-1).getId()+ 1;
        }
    }

    public Audiogram getCurrentAudiogram() {
        return currentAudiogram;
    }

    public void setCurrentAudiogram(Audiogram currentAudiogram) {
        this.currentAudiogram = currentAudiogram;
    }
}
