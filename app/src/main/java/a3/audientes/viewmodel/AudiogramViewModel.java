package a3.audientes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import a3.audientes.database.Repository;
import a3.audientes.model.Audiogram;

public class AudiogramViewModel extends AndroidViewModel {

    private Repository repositoty;
    private LiveData<List<Audiogram>> allAudiograms;

    public AudiogramViewModel(@NonNull Application application) {
        super(application);
        repositoty = new Repository(application);
        allAudiograms = repositoty.getAllAudiogram();
    }

    public void Insert(Audiogram audiogram){
        repositoty.InsertAudiogram(audiogram);
    }

    public void Update(Audiogram audiogram){
        repositoty.UpdateAudiogram(audiogram);
    }

    public void Delete(Audiogram audiogram){
        repositoty.DeleteAudiogram(audiogram);
    }

    public LiveData<List<Audiogram >> getAllAudiogram(){
        return allAudiograms;
    }
}
