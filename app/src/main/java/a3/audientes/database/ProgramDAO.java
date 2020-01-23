package a3.audientes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import a3.audientes.dto.Program;

public interface ProgramDAO {

    @Insert
    void insertProgram(Program program);

    @Update
    void updateProgram(Program program);

    @Delete
    void deleteProgram(Program program);

    @Query("DELETE FROM program_table WHERE id = :ID")
    void deleteProgramWithID(int ID);

    @Query("SELECT * FROM program_table")
    LiveData<List<Program>> getAllPrograms();

}
