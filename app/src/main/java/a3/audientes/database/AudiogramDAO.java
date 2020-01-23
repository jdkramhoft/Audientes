package a3.audientes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import a3.audientes.dto.Audiogram;

public interface AudiogramDAO {

    @Insert
    void insertAudiogram(Audiogram audiogram);

    @Update
    void updateAudiogram(Audiogram audiogram);

    @Delete
    void deleteAudiogram(Audiogram audiogram);

    @Query("DELETE FROM audiogram_table WHERE id = :ID")
    void deleteAudiogramWithID(int ID);

    @Query("SELECT * FROM audiogram_table")
    LiveData<List<Audiogram>> getAllAudiogram();
}
