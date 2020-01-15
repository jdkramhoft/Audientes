package a3.audientes.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import a3.audientes.dto.Program;
import a3.audientes.database.Repository;

public class ProgramViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Program>> allPrograms;

    public ProgramViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allPrograms = repository.getAllPrograms();
    }

    public void Insert(Program program){
        repository.InsertProgram(program);
    }

    public void Update(Program program){
        repository.UpdateProgram(program);
    }

    public void Delete(Program program){
        repository.DeleteProgram(program);
    }

    public LiveData<List<Program>> getAllPrograms(){
        return allPrograms;
    }
}