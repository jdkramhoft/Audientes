package a3.audientes.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;

import a3.audientes.dto.Audiogram;
import a3.audientes.dto.Program;


public class Repository {

    private DAO DAO;
    private LiveData<List<Program>> allPrograms;
    private LiveData<List<Audiogram>> allAudiogram;

    public Repository(Application application){
        DataBase dataBase = DataBase.getInstance(application);
        DAO = dataBase.dao();
        allPrograms = DAO.getAllPrograms();
        allAudiogram = DAO.getAllAudiogram();
    }

    // AsyncTask for programs

    public void InsertProgram(Program program){
        new InsertProgramAsyncTask(DAO).execute(program);
    }

    public void UpdateProgram(Program program){
        new UpdateProgramAsyncTask(DAO).execute(program);
    }

    public void DeleteProgram(Program program){
        new DeleteProgramAsyncTask(DAO).execute(program);
    }

    public LiveData<List<Program>> getAllPrograms(){
        return allPrograms;
    }

    private static class InsertProgramAsyncTask extends AsyncTask<Program, Void, Void> {
        private DAO dao;
        private InsertProgramAsyncTask(DAO DAO){
            this.dao = DAO;
        }
        @Override
        protected Void doInBackground(Program... program) {
            dao.insertProgram(program[0]);
            return null;
        }
    }

    private static class UpdateProgramAsyncTask extends AsyncTask<Program, Void, Void> {
        private DAO dao;
        private UpdateProgramAsyncTask(DAO DAO){
            this.dao = DAO;
        }
        @Override
        protected Void doInBackground(Program... program) {
            dao.updateProgram(program[0]);
            return null;
        }
    }

    private static class DeleteProgramAsyncTask extends AsyncTask<Program, Void, Void> {
        private DAO dao;
        private DeleteProgramAsyncTask(DAO DAO){
            this.dao = DAO;
        }
        @Override
        protected Void doInBackground(Program... program) {
            dao.deleteProgram(program[0]);
            return null;
        }
    }

    // AsyncTask for Audiograms

    public void InsertAudiogram(Audiogram audiogram){
        new InsertAudiogramAsyncTask(DAO).execute(audiogram);
    }

    public void UpdateAudiogram(Audiogram audiogram){
        new UpdateAudiogramAsyncTask(DAO).execute(audiogram);
    }

    public void DeleteAudiogram(Audiogram audiogram){
        new DeleteAudiogramAsyncTask(DAO).execute(audiogram);
    }

    public LiveData<List<Audiogram>> getAllAudiogram(){
        return allAudiogram;
    }

    private static class InsertAudiogramAsyncTask extends AsyncTask<Audiogram, Void, Void> {
        private DAO dao;
        private InsertAudiogramAsyncTask(DAO DAO){
            this.dao = DAO;
        }

        @Override
        protected Void doInBackground(Audiogram... audiogram) {
            dao.insertAudiogram(audiogram[0]);
            return null;
        }
    }


    private static class UpdateAudiogramAsyncTask extends AsyncTask<Audiogram, Void, Void> {
        private DAO dao;
        private UpdateAudiogramAsyncTask(DAO DAO){
            this.dao = DAO;
        }

        @Override
        protected Void doInBackground(Audiogram... audiogram) {
            dao.updateAudiogram(audiogram[0]);
            return null;
        }
    }

    private static class DeleteAudiogramAsyncTask extends AsyncTask<Audiogram, Void, Void> {
        private DAO dao;
        private DeleteAudiogramAsyncTask(DAO DAO){
            this.dao = DAO;
        }

        @Override
        protected Void doInBackground(Audiogram... audiogram) {
            dao.deleteAudiogram(audiogram[0]);
            return null;
        }
    }
}
