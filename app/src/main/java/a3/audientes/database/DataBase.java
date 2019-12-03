package a3.audientes.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import a3.audientes.model.Program;


@Database(entities = {Program.class}, version = 1)
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
        }
    };

    private static class CreateDefualtProgramsAsyncTask extends AsyncTask<Void, Void, Void> {

        private DAO DAO;
        private CreateDefualtProgramsAsyncTask(DataBase db){
            DAO = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DAO.insertProgram(new Program("Quiet", 10,10,10,10,10,2, false));
            DAO.insertProgram(new Program("Home", 15,15,15,15,15,2, false));
            DAO.insertProgram(new Program("Load", 25,25,25,25,25,2, false));
            return null;
        }
    }

}
