package a3.audientes.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import a3.audientes.models.Program;
import a3.audientes.repository.Repository;

public class ProgramViewModel extends AndroidViewModel {

    private Repository repositoty;
    private LiveData<List<Program>> allPrograms;

    public ProgramViewModel(@NonNull Application application) {
        super(application);
        repositoty = new Repository(application);
        allPrograms = repositoty.getAllPrograms();
    }

    public LiveData<List<Program>> getAllPrograms(){
        return allPrograms;
    }
}
