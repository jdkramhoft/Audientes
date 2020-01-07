package a3.audientes.model;

import java.util.ArrayList;
import java.util.List;

public class SoundManager {
    private static SoundManager single_instance = null;
    private List<Sound> sounds = new ArrayList<>();

    private SoundManager(){
        sounds.add(new Sound(500,1,8000));
        sounds.add(new Sound(1000,1,8000));
        sounds.add(new Sound(2000,1,8000));
        sounds.add(new Sound(5000,1,8000));
        sounds.add(new Sound(10000,1,8000));
    };

    public static SoundManager getInstance() {
        if (single_instance == null){
            single_instance = new SoundManager();
        }
        return single_instance;
    }

    public List<Sound> getSounds() {
        return sounds;
    }
}
