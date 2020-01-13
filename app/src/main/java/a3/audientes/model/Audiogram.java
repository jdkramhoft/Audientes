package a3.audientes.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "audiogram_table")
public class Audiogram implements Comparable<Audiogram> {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(AudiogramConverter.class)
    private ArrayList<Integer> x = new ArrayList<>();
    @TypeConverters(AudiogramConverter.class)
    private ArrayList<Integer> y = new ArrayList<>();
    @TypeConverters(AudiogramTimeConverter.class)
    private Date date;

    public Audiogram() {}

    public void addIndex(int[] xy){
        x.add(xy[0]);
        y.add(xy[1]);
    }

    public ArrayList<int[]> getGraf(){
        ArrayList<int[]> temp = new ArrayList<>();
        for(int i = 0; i < x.size(); i++){
            int[] xy = {x.get(i), y.get(i)};
            temp.add(xy);
        }
        return temp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getX() {
        return x;
    }

    public void setX(ArrayList<Integer> x) {
        this.x = x;
    }

    public ArrayList<Integer> getY() {
        return y;
    }

    public void setY(ArrayList<Integer> y) {
        this.y = y;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        DateFormat x = SimpleDateFormat.getDateInstance();
        return x.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Audiogram audiogram = (Audiogram) o;
        return id == audiogram.id &&
                x == audiogram.x &&
                y == audiogram.y &&
                date == audiogram.date;
    }


    @Override
    public String toString() {
        return "Audiogram{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Audiogram o) {
        return this.date.compareTo(o.date) > 0 ? -1 : 1 ;
    }
}
