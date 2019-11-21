package a3.audientes.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;

import a3.audientes.Database.DataBase;
import a3.audientes.models.Program;
import a3.audientes.Database.DAO;


public class Repository {

    private DAO DAO;
    private LiveData<List<Program>> allPrograms;

    public Repository(Application application){
        DataBase dataBase = DataBase.getInstance(application);
        DAO = dataBase.dao();
        allPrograms = DAO.getAllPrograms();

    }


    // Program

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

    // AsyncTask

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
}
