package a3.audientes.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import a3.audientes.model.Audiogram;
import a3.audientes.model.Program;


@Database(entities = {Program.class, Audiogram.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    private static DataBase instance;

    public abstract DAO dao();

    public static synchronized DataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataBase.class, "database").
                    fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CreateDefualtProgramsAsyncTask(instance).execute();
            new CreateDefualtAudiogramAsyncTask(instance).execute();
        }
    };

    private static class CreateDefualtProgramsAsyncTask extends AsyncTask<Void, Void, Void> {

        private DAO DAO;
        private CreateDefualtProgramsAsyncTask(DataBase db){
            DAO = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DAO.insertProgram(new Program("Quiet", 1000,1000,1000,1000,1000,2, false));
            DAO.insertProgram(new Program("Home", 1500,1500,1500,1500,1500,2, false));
            DAO.insertProgram(new Program("Loud", 2000,2000,2000,2000,2000,2, false));
            DAO.insertProgram(new Program("Windy", 3000,3000,3000,3000,3000,2, false));
            return null;
        }
    }

    private static class CreateDefualtAudiogramAsyncTask extends AsyncTask<Void, Void, Void> {

        private DAO DAO;
        private CreateDefualtAudiogramAsyncTask(DataBase db){
            DAO = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Audiogram newAudiogram = new Audiogram();
            newAudiogram.addIndex(new int[]{500,5});
            newAudiogram.addIndex(new int[]{1000,4});
            newAudiogram.addIndex(new int[]{2000,6});
            newAudiogram.addIndex(new int[]{5000,4});
            newAudiogram.addIndex(new int[]{10000,5});
            DAO.insertAudiogram(newAudiogram);
            return null;
        }
    }

}
