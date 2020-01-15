package a3.audientes.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

import a3.audientes.R;
import a3.audientes.dto.Audiogram;
import a3.audientes.dto.Program;


@Database(entities = {Program.class, Audiogram.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    private static DataBase instance;
    static String quiet;
    static String home;
    static String loud;
    static String windy;
    Context context;
    public abstract DAO dao();


    public static synchronized DataBase getInstance(Context context){
        quiet = context.getString(R.string.quiet_btn);
        home = context.getString(R.string.home_btn);
        loud = context.getString(R.string.loud_btn);
        windy = context.getString(R.string.windy);

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
            DAO.insertProgram(new Program( quiet, 1000,1000,1000,1000,1000,2, false));
            DAO.insertProgram(new Program(home, 1500,1500,1500,1500,1500,2, false));
            DAO.insertProgram(new Program(loud, 2000,2000,2000,2000,2000,2, false));
            DAO.insertProgram(new Program(windy, 3000,3000,3000,3000,3000,2, false));
            return null;
        }
    }

}
