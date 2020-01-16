package a3.audientes.dao;

import java.util.ArrayList;
import java.util.List;
import a3.audientes.dto.Audiogram;

public class AudiogramDAO {

    private static AudiogramDAO single_instance = null;
    private List<Audiogram> audiograms = new ArrayList<>();
    private Audiogram currentAudiogram;

    private AudiogramDAO(){}

    public static AudiogramDAO getInstance() {
        if (single_instance == null)
            single_instance = new AudiogramDAO();
        return single_instance;
    }

    public void addAudiogram(Audiogram audiogram){
        audiograms.add(audiogram);
    }

    public void resetAudiogram(){
        currentAudiogram = new Audiogram();
    }

    public void setCurrent(int id){
        for(int i = 0; i < audiograms.size(); i++){
            if(id == audiograms.get(i).getId()){
                currentAudiogram = audiograms.get(i);
            }
        }
    }

    public void addIndexToCurrentAudiogram(int[] xy){
        currentAudiogram.addIndex(xy);
    }

    public void saveCurrentAudiogram(){
        audiograms.add(currentAudiogram);
    }

    public int getNextId(){
        if(audiograms.size() == 0){
            return 1;
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


    public List<Audiogram> getAudiograms(){
        return audiograms;
    }

    public void setAudiograms(List<Audiogram> audiograms) {
        this.audiograms = audiograms;
    }
}
