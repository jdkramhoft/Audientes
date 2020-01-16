package a3.audientes.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import a3.audientes.database.Repository;
import a3.audientes.dto.Audiogram;

public class AudiogramViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Audiogram>> audiogramList;

    public AudiogramViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        audiogramList = repository.getAllAudiogram();
    }

    public void Insert(Audiogram audiogram){
        repository.InsertAudiogram(audiogram);
    }

    public void Update(Audiogram audiogram){
        repository.UpdateAudiogram(audiogram);
    }

    public void Delete(Audiogram audiogram){
        repository.DeleteAudiogram(audiogram);
    }

    public LiveData<List<Audiogram >> getAllAudiogram(){
        return audiogramList;
    }
}