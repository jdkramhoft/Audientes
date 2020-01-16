package a3.audientes.dao;

import java.util.ArrayList;
import java.util.List;

import a3.audientes.dto.Sound;

public class SoundDAO {
    private static SoundDAO single_instance = null;
    private List<Sound> sounds = new ArrayList<>();

    private SoundDAO(){
        sounds.add(new Sound(500,1,8000));
        sounds.add(new Sound(1000,1,8000));
        sounds.add(new Sound(2000,1,8000));
        sounds.add(new Sound(5000,1,8000));
        sounds.add(new Sound(10000,1,8000));
    }

    public static SoundDAO getInstance() {
        if (single_instance == null){
            single_instance = new SoundDAO();
        }
        return single_instance;
    }

    public List<Sound> getSounds() {
        return sounds;
    }
}
