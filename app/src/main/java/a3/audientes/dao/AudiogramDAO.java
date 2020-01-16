package a3.audientes.dao;

import java.util.ArrayList;
import java.util.List;
import a3.audientes.dto.Audiogram;

public class AudiogramDAO {

    private static AudiogramDAO single_instance = null;
    private List<Audiogram> audiogramList = new ArrayList<>();
    private Audiogram currentAudiogram;

    private AudiogramDAO(){}

    public static AudiogramDAO getInstance() {
        if (single_instance == null)
            single_instance = new AudiogramDAO();
        return single_instance;
    }

    public void addAudiogram(Audiogram audiogram){
        audiogramList.add(audiogram);
    }

    public void resetAudiogram(){
        currentAudiogram = new Audiogram();
    }

    public void setCurrent(int id){
        for(int i = 0; i < audiogramList.size(); i++){
            if(id == audiogramList.get(i).getId()){
                currentAudiogram = audiogramList.get(i);
            }
        }
    }

    public void addIndexToCurrentAudiogram(int[] xy){
        currentAudiogram.addIndex(xy);
    }

    public void saveCurrentAudiogram(){
        audiogramList.add(currentAudiogram);
    }

    public int getNextId(){
        if(audiogramList.size() == 0){
            return 1;
        }else{
            return audiogramList.get(audiogramList.size()-1).getId()+ 1;
        }
    }


    // Getters and setters

    public Audiogram getCurrentAudiogram() {
        return currentAudiogram;
    }

    public void setCurrentAudiogram(Audiogram currentAudiogram) {
        this.currentAudiogram = currentAudiogram;
    }


    public List<Audiogram> getAudiogramList(){
        return audiogramList;
    }

    public void setAudiogramList(List<Audiogram> audiogramList) {
        this.audiogramList = audiogramList;
    }
}
