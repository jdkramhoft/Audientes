package a3.audientes.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import a3.audientes.dto.Audiogram;
import a3.audientes.dto.Program;


@Database(entities = {Program.class, Audiogram.class}, version = 2)
public abstract class DataBase extends RoomDatabase {

    private static DataBase instance;
    private static final String EMPTY = "";
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
            new CreateDefaultProgramsAsyncTask(instance).execute();
        }
    };

    private static class CreateDefaultProgramsAsyncTask extends AsyncTask<Void, Void, Void> {

        private DAO DAO;
        private CreateDefaultProgramsAsyncTask(DataBase db){
            DAO = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DAO.insertProgram(new Program(EMPTY, 1000,1000,1000,1000,1000,2, false));
            DAO.insertProgram(new Program(EMPTY, 1500,1500,1500,1500,1500,2, false));
            DAO.insertProgram(new Program(EMPTY, 2000,2000,2000,2000,2000,2, false));
            DAO.insertProgram(new Program(EMPTY, 3000,3000,3000,3000,3000,2, false));
            return null;
        }
    }

}
