package a3.audientes.typeconverters;

import androidx.room.TypeConverter;
import java.util.Date;

/**
 * Class for converting Date objects to and from Longs
 */
public class AudiogramTimeConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
