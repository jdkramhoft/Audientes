package a3.audientes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import a3.audientes.model.Program;
import a3.audientes.database.Repository;

public class ProgramViewModel extends AndroidViewModel {

    private Repository repositoty;
    private LiveData<List<Program>> allPrograms;

    public ProgramViewModel(@NonNull Application application) {
        super(application);
        repositoty = new Repository(application);
        allPrograms = repositoty.getAllPrograms();
    }

    public void Insert(Program program){
        repositoty.InsertProgram(program);
    }

    public void Update(Program program){
        repositoty.UpdateProgram(program);
    }

    public void Delete(Program program){
        repositoty.DeleteProgram(program);
    }


    public LiveData<List<Program>> getAllPrograms(){
        return allPrograms;
    }
}
